package com.atguigu.gmall.index.service;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.vo.CategroyVO;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-10 15:25
 */
public interface IndexService {
    List<CategoryEntity> queryLevelFirstCategroy();

    List<CategroyVO> queryCategroyVO(Long pid);
}
