package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.SkuImagesEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * sku图片
 *
 * @author zsf
 * @email zsf3424@163.com
 * @date 2019-10-28 21:05:03
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageVo queryPage(QueryCondition params);
}

