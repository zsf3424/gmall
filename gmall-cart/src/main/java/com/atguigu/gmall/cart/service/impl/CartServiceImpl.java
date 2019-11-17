package com.atguigu.gmall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.cart.feign.GmallPmsClient;
import com.atguigu.gmall.cart.feign.GmallSmsClient;
import com.atguigu.gmall.cart.interceptor.LoginInterceptor;
import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.cart.vo.Cart;
import com.atguigu.gmall.cart.vo.UserInfo;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.atguigu.gmall.sms.vo.ItemSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zsf
 * @create 2019-11-15 20:54
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GmallPmsClient gmallPmsClient;

    @Autowired
    private GmallSmsClient gmallSmsClient;

    public static final String KEY_PREFIX = "cart:key:";

    public static final String CURRENT_PRICE_PRFIX = "cart:price:";

    @Override
    public void addCart(Cart cart) {

        String key = getKey();

        // 取出用户新增购物车商品的数量
        Integer count = cart.getCount();
        String skuId = cart.getSkuId().toString();

        // 判断购物车中是否有该记录
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

        if (hashOps.hasKey(skuId)) {
            // 有,更新数量
            String cartJson = hashOps.get(skuId).toString();
            cart = JSON.parseObject(cartJson, Cart.class);
            cart.setCount(cart.getCount() + count);
        } else {
            // 没有,新增记录
            Resp<SkuInfoEntity> skuInfoEntityResp = this.gmallPmsClient.querySkuById(cart.getSkuId());
            SkuInfoEntity skuInfoEntity = skuInfoEntityResp.getData();
            cart.setCheck(true);
            cart.setDefaultImage(skuInfoEntity.getSkuDefaultImg());
            cart.setPrice(skuInfoEntity.getPrice());
            cart.setTitle(skuInfoEntity.getSkuTitle());

            // 查询销售属性
            Resp<List<SkuSaleAttrValueEntity>> skuSaleAttrResp = this.gmallPmsClient.querySaleAttrBySkuId(cart.getSkuId());
            List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = skuSaleAttrResp.getData();
            cart.setSkuAttrValue(skuSaleAttrValueEntities);

            // 查询营销信息
            Resp<List<ItemSaleVO>> listResp = this.gmallSmsClient.queryItemSaleVOs(cart.getSkuId());
            cart.setSales(listResp.getData());

            //新增当前价格
            this.redisTemplate.opsForValue().set(CURRENT_PRICE_PRFIX + skuId, skuInfoEntity.getPrice().toString());
        }

        // 同步到redis中
        hashOps.put(skuId, JSON.toJSONString(cart));

    }

    @Override
    public List<Cart> queryCarts() {

        // 查询未登录状态的购物车
        UserInfo userInfo = LoginInterceptor.get();
        String keyUnLogin = KEY_PREFIX + userInfo.getUserKey();

        List<Cart> userKeyCarts = null;

        BoundHashOperations<String, Object, Object> userKeyOps = this.redisTemplate.boundHashOps(keyUnLogin);
        List<Object> cartJsonList = userKeyOps.values();

        if (!CollectionUtils.isEmpty(cartJsonList)) {
            userKeyCarts = cartJsonList.stream().map(cartJson -> {
                Cart cart = JSON.parseObject(cartJson.toString(), Cart.class);
                cart.setCurrentPrice(new BigDecimal(this.redisTemplate.opsForValue().get(CURRENT_PRICE_PRFIX + cart.getSkuId())));
                return cart;
            }).collect(Collectors.toList());
        }

        // 判断登录状态
        if (userInfo.getUserId() == null) {
            // 未登录直接返回
            return userKeyCarts;
        }

        // 登录，查询登录状态的购物车
        String keyLogined = KEY_PREFIX + userInfo.getUserId();
        BoundHashOperations<String, Object, Object> userIdOps = this.redisTemplate.boundHashOps(keyLogined);
        // 判断未登录的购物车是否为空
        if (!CollectionUtils.isEmpty(userKeyCarts)) {
            // 不为空，合并
            userKeyCarts.forEach(cart -> {
                //判断登录购物车有无记录
                if (userIdOps.hasKey(cart.getSkuId().toString())) {
                    // 有,更新数量
                    String cartJson = userIdOps.get(cart.getSkuId().toString()).toString();
                    Cart idCart = JSON.parseObject(cartJson, Cart.class);
                    idCart.setCount(idCart.getCount() + cart.getCount());
                    userIdOps.put(cart.getSkuId().toString(), JSON.toJSONString(idCart));
                } else {
                    // 没有,新增记录
                    userIdOps.put(cart.getSkuId().toString(), JSON.toJSONString(cart));
                }
            });
            this.redisTemplate.delete(keyUnLogin);//删除未登录购物车
        }

        // 查询返回
        List<Object> userIdCartJsonList = userIdOps.values();
        if (CollectionUtils.isEmpty(userIdCartJsonList)) {
            return null;
        }
        return userIdCartJsonList.stream().map(userIdCartJson -> {
            Cart cart = JSON.parseObject(userIdCartJson.toString(), Cart.class);
            cart.setCurrentPrice(new BigDecimal(this.redisTemplate.opsForValue().get(CURRENT_PRICE_PRFIX + cart.getSkuId())));
            return cart;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateCart(Cart cart) {

        String key = getKey();

        Integer count = cart.getCount();

        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

        if (hashOps.hasKey(cart.getSkuId().toString())) {
            // 获取购物车中的更新数量的购物记录
            String cartJson = hashOps.get(cart.getSkuId().toString()).toString();
            cart = JSON.parseObject(cartJson, Cart.class);
            cart.setCount(count);
            hashOps.put(cart.getSkuId().toString(), JSON.toJSONString(cart));
        }


    }

    @Override
    public void deleteCart(Long skuId) {
        String key = getKey();

        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

        if (hashOps.hasKey(skuId.toString())) {
            hashOps.delete(skuId.toString());
        }
    }

    @Override
    public void checkCart(List<Cart> carts) {
        String key = getKey();

        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

        carts.forEach(cart -> {
            Boolean check = cart.getCheck();
            if (hashOps.hasKey(cart.getSkuId().toString())) {
                String cartJson = hashOps.get(cart.getSkuId().toString()).toString();
                cart = JSON.parseObject(cartJson, Cart.class);
                cart.setCheck(check);
                hashOps.put(cart.getSkuId().toString(), JSON.toJSONString(cart));
            }


        });


    }

    private String getKey() {
        String key = KEY_PREFIX;

        //判断登录状态
        UserInfo userInfo = LoginInterceptor.get();
        if (userInfo.getUserId() != null) {
            key += userInfo.getUserId();
        } else {
            key += userInfo.getUserKey();
        }
        return key;
    }
}
