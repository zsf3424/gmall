package com.atguigu.gmall.pms.feign;

import com.atguigu.gmall.sms.api.GmallSmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author zsf
 * @create 2019-10-31 20:51
 */
@FeignClient("sms-service")
public interface GmallSmsClient extends GmallSmsApi {

}
