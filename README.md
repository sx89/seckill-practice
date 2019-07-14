<!-- TOC -->

- [1. seckill-practice](#1-seckill-practice)
  - [1.1. 分布锁实现方案分析](#11-%E5%88%86%E5%B8%83%E9%94%81%E5%AE%9E%E7%8E%B0%E6%96%B9%E6%A1%88%E5%88%86%E6%9E%90)
    - [1.1.1. redis分布式锁的使用场景](#111-redis%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E7%9A%84%E4%BD%BF%E7%94%A8%E5%9C%BA%E6%99%AF)
    - [1.1.2. 面试分享高频大厂面试题之分布式系统高可用](#112-%E9%9D%A2%E8%AF%95%E5%88%86%E4%BA%AB%E9%AB%98%E9%A2%91%E5%A4%A7%E5%8E%82%E9%9D%A2%E8%AF%95%E9%A2%98%E4%B9%8B%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F%E9%AB%98%E5%8F%AF%E7%94%A8)
  - [- 重启服务，看服务是否每次都获取锁失败](#%E9%87%8D%E5%90%AF%E6%9C%8D%E5%8A%A1%E7%9C%8B%E6%9C%8D%E5%8A%A1%E6%98%AF%E5%90%A6%E6%AF%8F%E6%AC%A1%E9%83%BD%E8%8E%B7%E5%8F%96%E9%94%81%E5%A4%B1%E8%B4%A5)
    - [1.1.3. 灾备切换Sentinel的使用](#113-%E7%81%BE%E5%A4%87%E5%88%87%E6%8D%A2Sentinel%E7%9A%84%E4%BD%BF%E7%94%A8)
      - [1.1.3.1. docker搭建redis主从集群](#1131-docker%E6%90%AD%E5%BB%BAredis%E4%B8%BB%E4%BB%8E%E9%9B%86%E7%BE%A4)
      - [1.1.3.2. docker 搭建redis-sentinel集群](#1132-docker-%E6%90%AD%E5%BB%BAredis-sentinel%E9%9B%86%E7%BE%A4)
    - [1.1.4. 互联网高可用灾备以及Sentinel三大任务讲解](#114-%E4%BA%92%E8%81%94%E7%BD%91%E9%AB%98%E5%8F%AF%E7%94%A8%E7%81%BE%E5%A4%87%E4%BB%A5%E5%8F%8ASentinel%E4%B8%89%E5%A4%A7%E4%BB%BB%E5%8A%A1%E8%AE%B2%E8%A7%A3)
    - [1.1.5. Redis高可用Sentinel故障转移原理](#115-Redis%E9%AB%98%E5%8F%AF%E7%94%A8Sentinel%E6%95%85%E9%9A%9C%E8%BD%AC%E7%A7%BB%E5%8E%9F%E7%90%86)

<!-- /TOC -->

# 1. seckill-practice
秒杀项目实战  

难点:
+ 关于docker通信方式的redis集群
+ 分布式锁的实现

## 1.1. 分布锁实现方案分析

### 1.1.1. redis分布式锁的使用场景
* 分布式锁是什么

* * 分布式锁是控制分布式系统或不同系统之间共同访问共享资源的一种锁实现

  * 如果不同的系统或同一个系统的不同主机之间共享了某个资源时，往往通过互斥来防止彼此干扰。

* 分布锁设计目的

​         可以保证在分布式部署的应用集群中，同一个方法在同一操作只能被一台机器上的一个线程执行。

* 设计要求
  * 这把锁要是一把可重入锁（避免死锁）
  * 这把锁有高可用的获取锁和释放锁功能
  * 这把锁获取锁和释放锁的性能要好… 

* 分布锁实现方案分析    
  * 获取锁的时候，使用 setnx(SETNX key val:当且仅当 key 不存在时，set 一个 key 为 val 的字符串，返回 1;
  * 若 key 存在，则什么都不做，返回 【0】加锁，锁的 value 值为当前占有锁服务器内网IP编号拼接任务标识
  * 在释放锁的时候进行判断。并使用 expire 命令为锁添 加一个超时时间，超过该时间则自动释放锁。 
  * 返回1则成功获取锁。还设置一个获取的超时时间， 若超过这个时间则放弃获取锁。setex（key,value,expire）过期以秒为单位
  * 释放锁的时候，判断是不是该锁（即Value为当前服务器内网IP编号拼接任务标识），若是该锁，则执行 delete 进行锁释放




### 1.1.2. 面试分享高频大厂面试题之分布式系统高可用

**redis高可用的探索，剖析高可用解决方案**

- 高可用的概念？

  - 高可用HA（High Availability）是分布式系统架构设计中必须考虑的因素之一，它通常是指，通过设计减少系统不能提供服务的时间。
    

    

- 通过三大要点解释高可用:

  ****************

  - 单点是系统高可用的大敌，应该尽量在系统设计的过程中避免单点

  - 保证系统高可用，架构设计的核心准则是：冗余。

  - 每次出现故障需要人工介入恢复势必会增加系统的不可服务实践,实现自动故障转移

  - 重启服务，看服务是否每次都获取锁失败   
------

​     

- 分布式高可用经典架构环节分析

    

  - 【客户端层】到【反向代理层】的高可用，是通过反向代理层的冗余来实现的。以nginx为例：有两台nginx，一台对线上提供服务，另一台冗余以保证高可用， 常见的实践是keepalived存活探测
  - 【反向代理层】到【web应用】的高可用，是通过站点层的冗余来实现的。假设反向代理层是nginx，nginx.conf里能够配置多个web后端，并且nginx能够探测到多个后端的存活性。
    * 自动故障转移：当web-server挂了的时候，nginx能够探测到，会自动的进行故障转移，将流量自动迁移到其他的web-server，整个过程由nginx自动完成，对调用方是透明的。

  * 【服务层】到【缓存层】的高可用，是通过缓存数据的冗余来实现的。
              redis天然支持主从同步，redis官方也有sentinel哨兵机制，来做redis的存活性检测。
  * 【服务层】到【数据库层】的高可用，数据库层用“主从同步，读写分离”架构，所以数据库层的高可用，又分为“读库高可用”与“写库高可用”两类。
    * 读库采用冗余的方式实现高可用，写库采用keepalived存活探测   binlog进行同步



### 1.1.3. 灾备切换Sentinel的使用

**简介：互联网服务灾备故障转移，sentinel的配置**

#### 1.1.3.1. docker搭建redis主从集群
[docker搭建redis主从集群](https://blog.csdn.net/qq_28804275/article/details/80907796)  
[容器之间软连接通信](https://www.9ge6.com/archives/392.html)  
[容器之间的四种通信方式](https://www.cnkirito.moe/docker-network-bridge/)  
[link的底层原理](https://www.jianshu.com/p/21d66ca6115e)  
改进点:
+ redis.conf的protected mode 关闭,不然会导致spring boot 访问失败
+ 同一主机的docker容器间的访问(redis里面设置127.0.0.1 会识别成自己容器内的网址,而不是宿主机的网址),创建slave容器的时候应该是`docker run --link redis-master:redis-master  -it --name redis-slave -v /root/:/usr/local/etc/redis -d -p 6301:6379 redis /bin/bash`,使用--link就可以使主从容器之间  
+  redis-slave.conf 里面的slaveof masterport使用master容器内部port 6379即可,不要用绑定到外部的port  

#### 1.1.3.2. docker 搭建redis-sentinel集群
[docker搭建redis-sentinel集群](https://cloud.tencent.com/developer/article/1343834)  
改进点: 
+ 运行sentinel的时候把26000端口暴露出来,以供外界访问  
docker run -it --name redis-sentienl0container `-p 26000:26000 ` -v /root/:/usr/local/etc/redis/ -d  redis:4.0 /bin/bash

+ 自定义sentinel.conf的时候, master ip不要写localhost或者127.0.0.1 ; 而是要写服务器所在局域网的ip,比如 192.168.245.4;这样spring boot 通过sentinel连接redis节点的时候,就不会出现连接127.0.0.1:6300而找不到redis节点.

* `Redis`主从复制可将主节点数据同步给从节点，从节点此时有两个作用：

  - 一旦主节点宕机，从节点作为主节点的备份可以随时顶上来。
  - 扩展主节点的读能力，分担主节点读压力。

  但是问题来了：

  - 一旦主节点宕机，从节点晋升成主节点，同时需要修改应用方的主节点地址，还需要命令所有从节点去复制新的主节点，整个过程需要人工干预。

- redis主节点挂掉之后应该怎么操作？命令模拟

  ```shell
   slaveof no one       # 取消主备，变更为主节点
  
   slaveof 新host  新节点  # 将其他节点设置为新主节点的备份节点
  ```

- Sentinel正是实现了这个功能

- 开启Sentinel配置       3主3从   3主6从

  ```powershell
  sentinel monitor mymaster 127.0.0.1 6379 1   
  sentinel down-after-milliseconds mymaster 10000
  sentinel failover-timeout mymaster 60000
  sentinel parallel-syncs mymaster 1
  
  ```

    

- 命令讲解

  * sentinel monitor mymaster 127.0.0.1 6379 1 名称为mymaster的主节点名，1表示将这个主服务器判断为失效至少需要 1个 Sentinel 同意 （只要同意 Sentinel 的数量不达标，自动故障迁移就不会执行）
  * down-after-milliseconds 选项指定了 Sentinel 认为服务器已经断线所需的毫秒数
  *  failover过期时间，当failover开始后，在此时间内仍然没有触发任何failover操作，当前sentinel  将会认为此次failoer失败
  * parallel-syncs 选项指定了在执行故障转移时， 最多可以有多少个从服务器同时对新的主服务器进行同步， 这个数字越小， 完成故障转移所需的时间就越长。  
  * 如果从服务器被设置为允许使用过期数据集， 那么你可能不希望所有从服务器都在同一时间向新的主服务器发送同步请求,即parallel-syncs mymaster 设置的过大,虽然这会让故障转移时间变长;  因为尽管复制过程的绝大部分步骤都不会阻塞从服务器， 但从服务器在载入主服务器发来的 RDB 文件时， 仍然会造成从服务器在一段时间内不能处理命令请求： 如果全部从服务器一起对新的主服务器进行同步， 那么就可能会造成所有从服务器在短时间内全部不可用的情况出现。

- 启动所有主从上的sentinel   

  *  前提是它们各自的server已成功启动
        cd /usr/local/redis/src/redis-sentinel /etc/redis/sentinel.conf

- info Replication  查看节点信息

- shutdown主节点看服务是否正常



### 1.1.4. 互联网高可用灾备以及Sentinel三大任务讲解

**简介：互联网冷备和热备讲解，Sentinel是怎么工作的？Sentinel三大工作任务是什么？**

- Sentinel三大工作任务 

  - 监控（Monitoring）： Sentinel 会不断地检查你的主服务器和从服务器是否运作正常。
  - 提醒（Notification）： 当被监控的某个 Redis 服务器出现问题时， Sentinel 可以通过 API 向管理员或者其他应用程序发送通知。      
  - 自动故障迁移（Automatic failover）： 当一个主服务器不能正常工作时， Sentinel 会开始一次自动故障迁移操作， 它会将失效主服务器的其中一个从服务器升级为新的主服务器， 并让失效主服务器的其他从服务器改为复制新的主服务器； 当客户端试图连接失效的主服务器时， 集群也会向客户端返回新主服务器的地址， 使得集群可以使用新主服务器代替失效服务器。
    

- 互联网冷备和热备讲解，冷备和热备的特点分析

  * 冷备  

    *  概念：冷备份发生在数据库已经正常关闭的情况下，当正常关闭时会提供给我们一个完整的数据库
    * 优点：
      * 是非常快速的备份方法（只需拷文件）
      *  低度维护，高度安全
    * 缺点：
      - 单独使用时，只能提供到“某一时间点上”的恢复
      - 再实施备份的全过程中，数据库必须要作备份而不能作其他工作。也就是说，在冷备份过程中，数据库必须是关闭状态

  * 热备

    *   概念：热备份是在数据库运行的情况下，采用archivelog mode方式备份数据库的方法      

    *   优点：
      * 备份的时间短
      * 备份时数据库仍可使用
      * 可达到秒级恢复
    *  缺点  
      * 若热备份不成功，所得结果不可用于时间点的恢复
      * 因难于维护，所以要非凡仔细小心


### 1.1.5. Redis高可用Sentinel故障转移原理

**简介：Sentinel是怎么工作的？**

- 主观下线：

  - 概念主观下线（Subjectively Down， 简称 SDOWN）指的是单个 Sentinel 实例对服务器做出的下线判断

  - 主管下线特点：

    * 如果一个服务器没有在 master-down-after-milliseconds 选项所指定的时间内， 对向它发送 PING 命令的 Sentinel 返回一个有效回复（valid reply）， 那么 Sentinel 就会将这个服务器标记为主观下线

    * 服务器对 PING 命令的有效回复可以是以下三种回复的其中一种：

         ```shell
         返回 +PONG 。
         返回 -LOADING 错误。
         返回 -MASTERDOWN 错误。
         ```

- 客观下线

  - 客观下线概念：

    * 指的是多个 Sentinel 实例在对同一个服务器做出 SDOWN 判断， 并且通过 SENTINEL is-master-down-by-addr 命令互相交流之后， 得出的服务器下线判断。 （一个 Sentinel 可以通过向另一个 Sentinel 发送 SENTINEL is-master-down-by-addr 命令来询问对方是否认为给定的服务器已下线。）

  - 客观下线特点：

    * 从主观下线状态切换到客观下线状态并没有使用严格的法定人数算法（strong quorum algorithm）， 而是使用了流言协议： 如果 Sentinel 在给定的时间范围内， 从其他 Sentinel 那里接收到了足够数量的主服务器下线报告， 那么 Sentinel 就会将主服务器的状态从主观下线改变为客观下线。 如果之后其他 Sentinel 不再报告主服务器已下线， 那么客观下线状态就会被移除。

  - 客观下线注意点：

    * 客观下线条件只适用于主服务器： 对于任何其他类型的 Redis 实例， Sentinel 在将它们判断为下线前不需要进行协商， 所以从服务器或者其他 Sentinel 永远不会达到客观下线条件。 
    * 只要一个 Sentinel 发现某个主服务器进入了客观下线状态， 这个 Sentinel 就可能会被其他 Sentinel 推选出， 并对失效的主服务器执行自动故障迁移操作。
