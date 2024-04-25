package com.wl.cloud.controller;

import com.wl.cloud.apis.PayFeignApi;
import com.wl.cloud.apis.PayGatewayApi;
import com.wl.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderGateWayController
{
    @Resource
    private PayGatewayApi payGatewayApi;

    @GetMapping(value = "/feign/pay/gateway/get/{id}")
    public ResultData getById(@PathVariable("id") Integer id)
    {
        return payGatewayApi.getGatewaytById(id);
    }

    @GetMapping(value = "/feign/pay/gateway/info")
    public ResultData<String> getGatewayInfo()
    {
        return payGatewayApi.getGatewayInfo();
    }
}