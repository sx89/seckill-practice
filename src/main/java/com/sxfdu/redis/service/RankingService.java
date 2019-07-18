package com.sxfdu.redis.service;

import com.sxfdu.redis.domain.UserScore;
import com.sxfdu.redis.domain.UserScoreExample;
import com.sxfdu.redis.mapper.ScoreFlowMapper;
import com.sxfdu.redis.mapper.UserMapper;
import com.sxfdu.redis.mapper.UserScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author sunxu93@163.com
 * @date 19/7/7/007 17:22
 */
@Service
public class RankingService {

    public static final String RANKING_NAME = "user_score";
    public static final String SALESCORE = "sale_score_rank:";
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserScoreMapper userScoreMapper;
    @Autowired
    private ScoreFlowMapper scoreFlowMapper;

    /**
     *只是往redis中存放数据,没有与数据库关联起来,
     */
    public void rankAdd(String uid, Integer score) {
        redisService.zAdd(RANKING_NAME,uid, score);
    }

    public void increScore(String uid, Integer score) {
        redisService.incrementScore(RANKING_NAME, uid, score);
    }

    public Long rankNum(String uid) {
        Long num = redisService.zRank(RANKING_NAME, uid);
        return num;
    }

    public Long getScore(String uid) {
        //TODO
        return null;
    }

    public Set<ZSetOperations.TypedTuple<Object>> rankWithScore(Integer start, Integer end) {
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisService.zRankWithScore(RANKING_NAME, start, end);
        return typedTuples;
    }

    /**
     * 与数据库关联
     * 增删改数据,数据库和redis都进行数据改变
     * 查数据从redis中取出来(排行榜本来也是查要远远多于增删改
     * <p>
     * 关于查数据,其实健全的逻辑是,如果redis查不到,将从DB中查找,但是可能会导致缓存雪崩问题(sql承受不住大量跳过redis的请求),所以暂时
     * 不使用sql
     */

    /**
     * 从数据库获取用户积分数据
     * 写到redis的zset里面
     */
    public void rankSaleAdd() {
        UserScoreExample userScoreExample = new UserScoreExample();
        userScoreExample.setOrderByClause("id desc");
        List<UserScore> userScores = userScoreMapper.selectByExample(userScoreExample);
        userScores.forEach(userScore ->{
            String key = userScore.getUserId() + ":" + userScore.getName();
            Long userScore1 = userScore.getUserScore();
            redisService.zAdd(SALESCORE,key,userScore1);
        });
    }
    /**
     * 把uid对应的score 增加一些值
     * 写入流水线表和用户积分表
     * 同时在redis里给对应用户增加积分
     */
}
