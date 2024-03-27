package com.wl.cloud.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.wl.cloud.DTO.PayDTO;
import com.wl.cloud.apis.PayFeignApi;
import com.wl.cloud.apis.PayFeignApi10s;
import com.wl.cloud.enums.ReturnCodeEnum;
import com.wl.cloud.resp.ResultData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/consumer")
public class OrderController {

//    public static final String PaymentSrv_URL = "http://localhost:8001";//先写死，硬编码

    //服务注册中心上的微服务名称
//    public static final String PaymentSrv_URL = "http://cloud-payment-service";

    @Resource
    private PayFeignApi payFeignApi;

    @Resource
    private PayFeignApi10s payFeignApi10s;

    @Resource
    DiscoveryClient discoveryClient;

    @GetMapping("/get/instances")
    public ResultData<List<String>> getInstances() {
        List<String> result = new ArrayList<>();
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        if (!CollectionUtil.isEmpty(instances)) {
            for (ServiceInstance instance : instances) {
                String instanceId = instance.getInstanceId();
                result.add(instanceId);
                System.out.println(instance.toString());
            }
        }
        return ResultData.success(result);
    }

    @GetMapping(value = "/pay/get/info")
    private String getInfoByConsul()
    {
        return payFeignApi.mylb();
    }

    /**
     * 一般情况下，通过浏览器的地址栏输入url，发送的只能是get请求
     * 我们底层调用的是post方法，模拟消费者发送get请求，客户端消费者
     * 参数可以不添加@RequestBody
     * @param payDTO
     * @return
     */
    @GetMapping("/pay/add")
    public ResultData addOrder(PayDTO payDTO){
        return payFeignApi.addPay(payDTO);
    }

    // 删除+修改操作作为家庭作业，O(∩_∩)O。。。。。。。
    @GetMapping("/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
        ResultData resultData = null;
        try
        {
            System.out.println("调用开始-----:"+ DateUtil.now());
            resultData = payFeignApi.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用结束-----:"+DateUtil.now());
           return ResultData.fail(ReturnCodeEnum.RC500.getCode(),e.getMessage());
        }
        return resultData;
    }

    @DeleteMapping(value = "/del/{id}")
    public ResultData deletePay(@PathVariable("id") Integer id) {

        return payFeignApi.deletePay(id);
    }

    @PutMapping(value = "/update")
    public ResultData updatePay(@RequestBody PayDTO payDTO){


        return payFeignApi.updatePay(payDTO);
    }

    @GetMapping(value = "/getAll")
    public ResultData getAll(){
        System.out.println("-------getAll 支付微服务远程调用，按照id查询订单支付流水信息");
        ResultData resultData = null;
        try
        {
            System.out.println("调用开始-----:"+ DateUtil.now());
            resultData = payFeignApi10s.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用结束-----:"+DateUtil.now());
            return ResultData.fail(ReturnCodeEnum.RC500.getCode(),e.getMessage());
        }
        return resultData;
    }

}
