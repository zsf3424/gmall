package com.atguigu.gmall.oms.dao;

import com.atguigu.gmall.oms.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author zsf
 * @email zsf3424@163.com
 * @date 2019-10-28 20:57:48
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
