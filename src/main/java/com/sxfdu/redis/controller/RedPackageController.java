package com.sxfdu.redis.controller;

import com.sxfdu.redis.domain.RedPacketInfo;
import com.sxfdu.redis.domain.RedPacketRecord;
import com.sxfdu.redis.mapper.RedPacketInfoMapper;
import com.sxfdu.redis.mapper.RedPacketRecordMapper;
import com.sxfdu.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * 抢红包功能
 *
 * @author sunxu93@163.com
 * @date 19/7/18/018 11:01
 */
@RestController
public class RedPackageController {

    public static final Logger logger = LoggerFactory.getLogger(RedPackageController.class);
    public static final String TOTAL_NUM = "_totalNum";
    public static final String TOTAL_AMOUNT = "_totalAmount";

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisService redisService;
    @Autowired
    RedPacketInfoMapper redPacketInfoMapper;
    @Autowired
    RedPacketRecordMapper redPacketRecordMapper;

    /**
     * 发红包
     *
     * @param uid
     * @param totalNum
     * @param totalAmount
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRedPackage")
    public String saveRedPackage(Integer uid, Integer totalNum, Integer totalAmount) {

        RedPacketInfo redPacketInfo = new RedPacketInfo();
        long redPacketId = System.currentTimeMillis();
        Random random = new Random();
        random.nextInt(1000);

        //TODO 不可以保证唯一性,后期用雪花算法生成id
        redPacketInfo.setRedPacketId(redPacketId);
        redPacketInfo.setTotalAmount(totalAmount);
        redPacketInfo.setTotalPacket(totalNum);
        redPacketInfo.setUid(uid);
        redPacketInfo.setCreateTime(new Date());
        redPacketInfo.setRemainingAmount(totalAmount);
        redPacketInfo.setRemainingPacket(totalNum);

        redPacketInfoMapper.insert(redPacketInfo);

        redisService.set(redPacketId + TOTAL_NUM, totalNum + "");
        redisService.set(redPacketId + TOTAL_AMOUNT, totalAmount + "");

        return "success";
    }

    //TODO 入库转账的时候保证红包个数和红包剩余金额 与缓存相同
    //TODO 抢红包的时候高并发,拆红包的时候可以过滤掉没有抢到红包的人的请求


    @RequestMapping("/getRedPackageNum")
    @ResponseBody
    public String getRedPackage(Integer uid, String redPackageId) {
        //TODO 判断用户存不存在的逻辑

        Integer randomAmount;
        String key = redPackageId + TOTAL_NUM;
        String remainNum = redisService.get(key).toString();

        String key1 = redPackageId + TOTAL_AMOUNT;
        String remainAmout = redisService.get(key1).toString();

        if (StringUtils.isEmpty(remainNum) || Integer.parseInt(remainNum)<=0) {
            return "红包已经抢完了";
        } else {
            Integer totalAmountInt = Integer.parseInt(remainAmout);
            Integer totalNumInt = Integer.parseInt(remainNum);
            //TODO 最后一个红包要保证把金额都用完
            Integer maxMoney = totalAmountInt / totalNumInt * 2;
            Random random = new Random();
            randomAmount = random.nextInt(maxMoney);

            redisService.decr(key, 1);
            redisService.decr(key1, randomAmount);

            updatePacketInDb(uid, Long.parseLong(redPackageId), randomAmount);
        }
        return randomAmount.toString();
    }

    /**
     * 记录抢红包流水信息
     * @param uid
     * @param redPacketId
     * @param amount
     */
    public void updatePacketInDb(int uid, Long redPacketId, int amount) {
        /**
         * 记录流水DB
         */
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setAmount(amount);
        redPacketRecord.setUid(uid);
        redPacketRecord.setRedPacketId(Long.parseLong(redPacketId.toString()));
        redPacketRecord.setCreateTime(new Date());
        redPacketRecord.setNickName(uid + "的nickName");
        redPacketRecord.setImgUrl(uid + "url");
        redPacketRecord.setCreateTime(new Date());
        redPacketRecordMapper.insert(redPacketRecord);
        /**
         * 更新红包信息DB
         */
        //TODO 查出redpacketInfo的红包数和金额数,减去金额和红包包数
    }
}