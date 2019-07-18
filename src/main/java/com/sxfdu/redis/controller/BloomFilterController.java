package com.sxfdu.redis.controller;

import com.sxfdu.redis.service.BloomFilterService;
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

    @ResponseBody
    @RequestMapping("/bloomFilter/isExist")
    public Boolean isExist(int id) {
        boolean result = bloomFilterService.userIdExists(id);
        return result;
    }
}
