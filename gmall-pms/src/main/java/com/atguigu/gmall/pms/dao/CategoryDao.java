package com.atguigu.gmall.pms.dao;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zsf
 * @email zsf3424@163.com
 * @date 2019-10-28 21:05:03
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
