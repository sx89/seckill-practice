package com.sxfdu.redis.controller;

import com.sxfdu.redis.domain.RedPacketInfo;
import com.sxfdu.redis.domain.RedPacketRecord;
import com.sxfdu.redis.mapper.RedPacketInfoMapper;
import com.sxfdu.redis.mapper.RedPacketRecordMapper;
import com.sxfdu.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
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

        redisService.set(redPacketId + "_totalNum", totalNum+"");
        redisService.set(redPacketId + "_totalAmount", totalAmount+"");

        return "success";
    }


    @RequestMapping("/getRedPackage")
    @ResponseBody
    public String getRedPackage(Integer uid, Integer redPackageId) {

        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setAmount(111);
        redPacketRecord.setUid(uid);
        redPacketRecord.setRedPacketId(Long.parseLong(redPackageId.toString()));
        redPacketRecord.setCreateTime(new Date());


        redisService.decr(redPackageId + "_totalNum", 1);
        return "success";
    }
}