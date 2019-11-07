package com.atguigu.gmall.search.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zsf
 * @create 2019-11-07 19:39
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
