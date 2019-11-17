package com.atguigu.gmall.cart.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zsf
 * @create 2019-11-15 20:48
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
