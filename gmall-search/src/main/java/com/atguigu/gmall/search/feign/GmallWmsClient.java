package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.wms.api.GmallWmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zsf
 * @create 2019-11-07 19:40
 */

@FeignClient("wms-service")
public interface GmallWmsClient extends GmallWmsApi {
}
