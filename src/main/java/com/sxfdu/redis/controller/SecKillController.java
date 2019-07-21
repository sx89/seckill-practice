package com.sxfdu.redis.controller;

import com.sxfdu.redis.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunxu93@163.com
 * @date 19/7/20/020 11:27
 */
@RestController
public class SecKillController {
    @Autowired
    SecKillService secKillService;

    @RequestMapping("/seckill")
    @ResponseBody
    public String secKill(int uid, int skId) {
        return secKillService.secKill(uid, skId);
    }
}
