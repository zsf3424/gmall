package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.vo.CategroyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.CategoryDao;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public List<CategoryEntity> queryCategory(Integer level, Long parentCid) {
        //构造查询条件
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        // 如果level为0，说明查询所有的级别
        if (level != 0) {
            wrapper.eq("cat_level", level);
        }
        // 如果parentCid为null，说明用户没有传该字段，查询所有
        if (parentCid != null) {
            wrapper.eq("parent_cid",parentCid);
        }
        return this.categoryDao.selectList(wrapper);
    }

    @Override
    public List<CategroyVO> queryCategroyWithSub(Long pid) {

        List<CategroyVO> categroyVOS = this.categoryDao.queryCategroyWithSub(pid);

        return categroyVOS;
    }

}