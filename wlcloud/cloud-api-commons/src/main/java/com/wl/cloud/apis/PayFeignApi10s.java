package com.wl.cloud.apis;

import com.wl.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "cloud-payment-service",contextId = "payment-service-10s")
public interface PayFeignApi10s {

    @GetMapping(value = "/pay/getAll")
    public ResultData getAll();
}
