package com.atguigu.gmall.index.service;

import com.atguigu.gmall.pms.entity.CategoryEntity;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-10 15:25
 */
public interface IndexService {
    List<CategoryEntity> queryLevelFirstCategroy();
}
