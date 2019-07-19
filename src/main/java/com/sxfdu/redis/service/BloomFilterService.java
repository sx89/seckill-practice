package com.sxfdu.redis.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.sxfdu.redis.domain.SysUserBloom;
import com.sxfdu.redis.domain.SysUserBloomExample;
import com.sxfdu.redis.mapper.SysUserBloomMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunxu93@163.com
 * @date 19/7/18/018 18:38
 */
@Service
public class BloomFilterService {
    @Resource
    private SysUserBloomMapper sysUserBloomMapper;
    private BloomFilter<Integer> bloomFilter;

    /**
     * 初始化一个布隆过滤器
     * PostConstruct可以让程序启动的时候加载此方法
     */
    @PostConstruct
    public void initBloomFilter() {
        SysUserBloomExample sysUserBloomExample = new SysUserBloomExample();
        List<SysUserBloom> sysUserBlooms = sysUserBloomMapper.selectByExample(sysUserBloomExample);
        if (CollectionUtils.isEmpty(sysUserBlooms)) {
            return;
        }
        bloomFilter = BloomFilter.create(Funnels.integerFunnel(), sysUserBlooms.size());

        for (SysUserBloom temp : sysUserBlooms) {
            bloomFilter.put(temp.getId());
        }

    }

    /**
     * 判断id是否可能存在
     * @param id
     * @return
     */
    public boolean userIdExists(int id) {
        return bloomFilter.mightContain(id);
    }

}
