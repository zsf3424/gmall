package com.atguigu.gmall.item.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zsf
 * @create 2019-11-12 19:45
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
