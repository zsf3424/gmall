package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.vo.CategroyVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;

import java.util.List;


/**
 * 商品三级分类
 *
 * @author zsf
 * @email zsf3424@163.com
 * @date 2019-10-28 21:05:03
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageVo queryPage(QueryCondition params);

    List<CategoryEntity> queryCategory(Integer level, Long parentCid);

    List<CategroyVO> queryCategroyWithSub(Long pid);
}

