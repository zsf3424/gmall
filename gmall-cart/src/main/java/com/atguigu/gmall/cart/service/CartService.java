package com.atguigu.gmall.cart.service;

import com.atguigu.gmall.cart.vo.Cart;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-15 20:53
 */
public interface CartService {
    void addCart(Cart cart);

    List<Cart> queryCarts();

    void updateCart(Cart cart);

    void deleteCart(Long skuId);

    void checkCart(List<Cart> carts);
}
