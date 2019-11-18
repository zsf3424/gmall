package com.atguigu.gmall.oms.api;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.oms.entity.OrderEntity;
import com.atguigu.gmall.oms.vo.OrderSubmitVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zsf
 * @create 2019-11-18 21:12
 */
public interface GmallOmsApi {

    //创建订单
    @PostMapping("oms/order")
    public Resp<OrderEntity> createOrder(@RequestBody OrderSubmitVO submitVO);


}
