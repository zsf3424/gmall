package com.atguigu.gmall.index.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.feign.GmallPmsClient;
import com.atguigu.gmall.index.service.IndexService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.vo.CategroyVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zsf
 * @create 2019-11-10 15:25
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private GmallPmsClient gmallPmsClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String KEY_PREFIX = "index:categroy:";

    @Override
    public List<CategoryEntity> queryLevelFirstCategroy() {

        Resp<List<CategoryEntity>> listResp = this.gmallPmsClient.queryCategory(1, null);

        return listResp.getData();
    }

    @Override
    public List<CategroyVO> queryCategroyVO(Long pid) {
        // 1. 查询缓存，缓存中有的话直接返回
        String cache = this.stringRedisTemplate.opsForValue().get(KEY_PREFIX + pid);
        if (StringUtils.isNotEmpty(cache)) {
            return JSON.parseArray(cache, CategroyVO.class);
        }

        // 2. 如果缓存中没有，查询数据库
        Resp<List<CategroyVO>> listResp = this.gmallPmsClient.queryCategroyWithSub(pid);
        List<CategroyVO> categoryVOS = listResp.getData();

        // 3. 查询完成之后，放入缓存
        this.stringRedisTemplate.opsForValue().set(KEY_PREFIX + pid, JSON.toJSONString(categoryVOS), 5 + (int) (Math.random() * 5), TimeUnit.DAYS);

        return categoryVOS;
    }
}
