package com.sxfdu.redis.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sunxu93@163.com
 * @date 19/7/20/020 11:26
 */
@Service
public class SecKillService {
    private static final String SEC_START_PREFIX = "skuId_start_";
    private static final String SEC_ACCESS = "skuId_access_";
    private static final String SEC_COUNT = "skuId_count_";
    private static final String FILTER_NAME = "skuId_bloomfilter_";
    private static final String BOOKED_NAME = "skuId_booked_";

    @Autowired
    RedisService redisService;

    public String secKill(int uid, int skId) {
        //startStr是开始标志,0_2134242   开始标志_开始时间
        String startStr = (String) redisService.get(SEC_START_PREFIX + skId);
        //用时间标志来控制时间,用开始标志来防止流量倾斜
        if (StringUtils.isBlank(startStr)) {
            return "秒杀还未开始";
        } else {
            if (startStr.contains("_")) {
                String[] startStrArray = startStr.split("_");
                Integer startFlag = Integer.parseInt(startStrArray[0]);
                Integer startTime = Integer.parseInt(startStrArray[1]);
                if (0 == startFlag) {
                    long now = getNow();
                    if (now > startTime) {
                        //设置成1表示已经开始
                        redisService.set(SEC_START_PREFIX + skId, 1 + "");
                    } else {
                        return "还未到秒杀时间";
                    }
                } else {
                    return "开始标志错误";
                }

            } else if (!startStr.equals("1")) {
                return "开始标志格式错误";
            }
        }

        //access是进入秒杀的用户,count是实际可以秒伤的商品数量
        //一般access>count*1.2  因为access进来的用户可能是重复用户或者无效用户
        String skAccessKey = SEC_ACCESS + skId;
        String skCountKey = SEC_COUNT + skId;


        Integer skAccessInt = 0;
        Object skAccessObj = redisService.get(skAccessKey);
        if (skAccessObj == null) {
            redisService.set(skAccessKey, skAccessInt.toString());
        } else {
            skAccessInt = Integer.parseInt(skAccessObj.toString());
        }


        Integer skCountInt = 10;
        Object skCountObj = redisService.get(skCountKey);
        if (skCountObj == null) {
            redisService.set(skCountKey, skCountInt.toString());
        } else {
            skCountInt = Integer.parseInt(skCountObj.toString());
        }

        //过滤access用户,或者access数量加1
        if (skAccessInt > skCountInt * 1.2) {
            return "抢购人数已满";
        }
        redisService.incr(skAccessKey);
        //过滤已经抢过商品的用户
        if (redisService.bloomFilterExists(FILTER_NAME, uid)) {
            return "您已抢购过商品,请勿重复下单";
        }
        redisService.bloomFilterAdd(FILTER_NAME, uid);
        //真正订到秒杀商品的数量   以为是 get+判断book是否超量+incre  所以为了保证原子操作,使用lua脚本
        Boolean success = redisService.getAndIncrLua(BOOKED_NAME+skId);
        if (success) {
            redisService.bloomFilterAdd(uid);
            return "抢购成功";
        }
        return "抢购失败";

        //TODO 异步入库,使用mq
    }

    private long getNow() {
        return System.currentTimeMillis() / 1000;
    }
}
