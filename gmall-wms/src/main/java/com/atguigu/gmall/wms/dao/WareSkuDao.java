package com.atguigu.gmall.wms.dao;

import com.atguigu.gmall.wms.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 * 
 * @author zsf
 * @email zsf3424@163.com
 * @date 2019-10-28 21:13:21
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    public List<WareSkuEntity> checkStore(@Param("skuId") Long skuId, @Param("count") Integer count);

    public int lock(@Param("id") Long id, @Param("count") Integer count);

    public int unlock(@Param("id") Long id, @Param("count") Integer count);
}
