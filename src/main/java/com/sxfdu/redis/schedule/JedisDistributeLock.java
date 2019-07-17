package com.sxfdu.redis.schedule;

import com.sxfdu.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scripting.support.ResourceScriptSource;
import redis.clients.jedis.JedisCommands;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author sunxu93@163.com
 * @date 19/7/13/013 22:03
 */
public class JedisDistributeLock {

    private final Logger logger = LoggerFactory.getLogger(JedisDistributeLock.class);

    private static final String LOCK_PREFIX = "JedisDistributedLock_";

    private DefaultRedisScript<Boolean> lockScript;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisService redisService;

    public static final String UNLOCK_LUA;


    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }


    @Scheduled(cron = "0/10 * * * * *")
    public void lockJob() {

        String lock = LOCK_PREFIX + "JedisNxExJob";
        boolean lockRet = false;
        try {
            lockRet = this.setLock(lock, 600);

            //获取锁失败
            if (!lockRet) {
                String value = (String) redisService.getValue(lock);
                //打印当前占用锁的服务器IP
                logger.info("jedisLockJob get lock fail,lock belong to:{}", value);
                return ;
            } else {
                //获取锁成功
                logger.info("jedisLockJob start  lock lockNxExJob success");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            logger.error("jedisLockJob lock error", e);

        } finally {
            if (lockRet) {
                logger.info("jedisLockJob release lock success");
                releaseLock(lock,getHostIp());
            }
        }
    }
    public boolean setLock(String key, long expire) {
        try {
            Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.set(key.getBytes(), getHostIp().getBytes(), Expiration.seconds(expire) , RedisStringCommands.SetOption.ifAbsent());
                }
            });
            return result;
        } catch (Exception e) {
            logger.error("set redis occured an exception", e);
        }
        return false;
    }

    /**
     * 释放锁操作
     * @param key
     * @param value
     * @return
     */
    private boolean releaseLock(String key, String value) {
        lockScript = new DefaultRedisScript<Boolean>();
        lockScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("unlock.lua")));
        lockScript.setResultType(Boolean.class);
        // 封装参数
        List<Object> keyList = new ArrayList<Object>();
        keyList.add(key);
        keyList.add(value);
        Boolean result = (Boolean) redisTemplate.execute(lockScript, keyList);
        return result;
    }

    /**
     * 测试原生redis连接是否可用
     */
    public String get(String key) {
        try {

            RedisCallback<String> callback = (connection) -> {

                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.get(key);
            };

            String result = redisTemplate.execute(callback);
            return result;
        } catch (Exception e) {
            logger.error("get redis occured an exception", e);
        }
        return "";
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

