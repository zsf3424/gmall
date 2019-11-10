package com.atguigu.gmall.index.feign;

import com.atguigu.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zsf
 * @create 2019-11-10 15:14
 */
@FeignClient("pms-service")
public interface GmallPmsClient extends GmallPmsApi {
}
