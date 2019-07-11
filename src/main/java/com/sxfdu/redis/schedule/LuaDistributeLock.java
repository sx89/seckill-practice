package com.sxfdu.redis.schedule;

import com.sxfdu.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author sunxu93@163.com
 * @date 19/7/11/011 19:52
 */
@Service
public class LuaDistributeLock {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(LuaDistributeLock.class);
    private static final String LOCK_PREFIX = "lua_prefix";
    private DefaultRedisScript<Boolean> lockScript;

    @Scheduled(cron = "0/10 * * * * *")
    public void luaLockJob() {

        String jobName = LOCK_PREFIX + "LockNxExJob";
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Boolean isAbsent = valueOperations.setIfAbsent(jobName, getHostIp());

        try {
            if (!isAbsent) {
                logger.info("获取锁失败,锁现在是{}的", valueOperations.get(jobName));
            } else {
                logger.info("获取锁成功");
                logger.info("开始消费信息");
                Thread.sleep(5000);
                logger.info("消费完成");
            }
        } catch (InterruptedException e) {
            logger.error("线程睡眠异常:{}", e);
        } finally {
            if (isAbsent) {
                logger.info("既然之前获取过锁,现在用完了,解除锁");
                redisTemplate.delete(jobName);
            }
        }
    }

    /**
     * lua表达式的实现逻辑
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean luaExpress(String key, String value) {

        lockScript = new DefaultRedisScript<Boolean>();
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("add.lua")));
        lockScript.setResultType(Boolean.class);

        List<Object> keyList = new ArrayList<>();
        keyList.add(key);
        keyList.add(value);
        Boolean result = (Boolean) redisTemplate.execute(lockScript, keyList);
        return result;
    }

    /**
     * 获取本机内网IP地址方法
     *
     * @return
     */
    private static String getHostIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":") == -1) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
