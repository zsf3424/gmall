package com.atguigu.gmall.order.feign;

import com.atguigu.gmall.cart.api.GamllCartApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zsf
 * @create 2019-11-17 16:50
 */
@FeignClient("cart-service")
public interface GmallCartClient extends GamllCartApi {
}
