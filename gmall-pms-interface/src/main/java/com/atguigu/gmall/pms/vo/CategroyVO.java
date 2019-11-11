package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.CategoryEntity;
import lombok.Data;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-10 16:08
 */
@Data
public class CategroyVO extends CategoryEntity {

    private List<CategoryEntity> subs;

}
