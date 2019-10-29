package com.atguigu.gmall.sms.dao;

import com.atguigu.gmall.sms.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author zsf
 * @email zsf3424@163.com
 * @date 2019-10-28 21:08:51
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
