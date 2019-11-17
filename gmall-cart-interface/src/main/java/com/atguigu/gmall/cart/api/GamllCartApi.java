package com.atguigu.gmall.cart.api;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.cart.vo.CartItemVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-17 16:32
 */
public interface GamllCartApi {

    //查询购物车中选中的sku的skuId和count
    @GetMapping("cart/order/{userId}")
    public Resp<List<CartItemVO>> queryCartItemVO(@PathVariable("userId")Long userId);

}
