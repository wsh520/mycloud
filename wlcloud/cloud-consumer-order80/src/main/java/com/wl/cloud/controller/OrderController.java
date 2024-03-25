package com.wl.cloud.controller;


import com.wl.cloud.DTO.PayDTO;
import com.wl.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consumer")
public class OrderController {

    public static final String PaymentSrv_URL = "http://localhost:8001";//先写死，硬编码

    @Resource
    private RestTemplate restTemplate;

    /**
     * 一般情况下，通过浏览器的地址栏输入url，发送的只能是get请求
     * 我们底层调用的是post方法，模拟消费者发送get请求，客户端消费者
     * 参数可以不添加@RequestBody
     * @param payDTO
     * @return
     */
    @GetMapping("/pay/add")
    public ResultData addOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/add",payDTO,ResultData.class);
    }
    // 删除+修改操作作为家庭作业，O(∩_∩)O。。。。。。。
    @GetMapping("/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/"+id, ResultData.class, id);
    }

    @GetMapping("/pay/del/{id}")
    public ResultData deleteOrder(@PathVariable("id") Integer id){
        restTemplate.delete(PaymentSrv_URL + "/pay/del/"+id,  id);
        return ResultData.success();
    }

    @GetMapping("/pay/update")
    public ResultData updateOrder(PayDTO payDTO){
        restTemplate.put(PaymentSrv_URL + "/pay/update",payDTO);
        return ResultData.success();
    }
}
