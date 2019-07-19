package com.sxfdu.redis.controller;

import com.sxfdu.redis.service.BloomFilterService;
import com.sxfdu.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunxu93@163.com
 * @date 19/7/18/018 18:36
 */
@RestController
public class BloomFilterController {

    @Autowired
    BloomFilterService bloomFilterService;
    @Autowired
    RedisService redisService;

    /**
     * 谷歌的guava
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/bloomFilter/isExist")
    public Boolean isExist(int id) {
        boolean result = bloomFilterService.userIdExists(id);
        return result;
    }

    /**
     * redis官方插件,redisBloom,不过因为是新出的Filter,redisTemplate还有没相关的接口,就要用lua脚本来自己写
     * @param id
     * @return
     */
    @RequestMapping("/bloom/redisIdExists")
    public boolean redisidExists(int id){
        return redisService.bloomFilterExists(id);
    }

    @RequestMapping("/bloom/redisIdAdd")
    public boolean redisidAdd(int id){
        return redisService.bloomFilterAdd(id);
    }

}
