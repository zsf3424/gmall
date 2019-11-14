package com.atguigu.gmall.auth.feign;

import com.atguigu.gmall.ums.api.GmallUmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zsf
 * @create 2019-11-14 14:46
 */
@FeignClient("ums-service")
public interface GmallUmsClient extends GmallUmsApi {
}
