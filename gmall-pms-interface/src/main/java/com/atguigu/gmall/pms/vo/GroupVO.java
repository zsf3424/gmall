package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import lombok.Data;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-12 12:33
 */
@Data
public class GroupVO {
    private String groupName;

    private List<ProductAttrValueEntity> baseAttrValues;
}
