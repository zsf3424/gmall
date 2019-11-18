package com.atguigu.gmall.wms.vo;

import lombok.Data;

/**
 * @author zsf
 * @create 2019-11-17 18:52
 */
@Data
public class SkuLockVO {

    private Long skuId;
    private Integer count;
    private Boolean lock;//锁定成功true，锁定失败false
    private Long skuWareId;//锁定库存的Id

}
