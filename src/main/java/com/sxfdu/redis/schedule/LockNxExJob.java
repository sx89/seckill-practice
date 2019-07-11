package com.sxfdu.redis.schedule;

import com.sxfdu.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @author sunxu93@163.com
 * @date 19/7/11/011 11:54
 */
@Service
public class LockNxExJob {

    private static final Logger logger = LoggerFactory.getLogger(LockNxExJob.class);
    private static final String LOCK_PREFIX = "prefix_";

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron =  "0/5 * * * * *")
    public void lockJob() {
            String lockName = LOCK_PREFIX + "LockNxEXJob";
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Boolean isAbsent = valueOperations.setIfAbsent(lockName, getHostIp());
        try {
            //如果锁已经存在
            if (!isAbsent) {
                String getLockIp = valueOperations.get(lockName).toString();
                logger.info("获取锁失败,当前占有锁的ip是:" + getLockIp);
                return;
            } else {
                logger.info("获取锁成功");
                logger.info("消费信息");
                Thread.sleep(5000);
                logger.info("消费完成");

            }
        } catch (InterruptedException e) {
            logger.error("获取锁异常:线程睡眠的异常:" +
                    "" +
                    "", e);
        } finally {
            if (isAbsent) {
                redisTemplate.delete(lockName);
                logger.info("释放锁成功");
            }
        }
    }

    /**
     * 获取本机内网IP地址方法
     * @return
     */
    private static String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String localIP = "";
        try {
            localIP = getHostIp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取本机IP
        System.out.println(localIP);
    }

}
