package com.wl.cloud.apis;


import com.wl.cloud.DTO.PayDTO;
import com.wl.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "cloud-payment-service")
public interface PayFeignApi {

    /**
     * 新增一条支付相关流水记录
     * @param payDTO
     * @return
     */
    @PostMapping("/pay/add")
    public ResultData addPay(@RequestBody PayDTO payDTO);

    /**
     * openfeign天然支持负载均衡演示
     * @return
     */
    @GetMapping(value = "/pay/get/info")
    public String mylb();

    @GetMapping(value = "/pay/get/{id}")
    public ResultData getById(@PathVariable("id") Integer id);

    @PutMapping(value = "/pay/update")
    public ResultData updatePay(@RequestBody PayDTO payDTO);

    @DeleteMapping(value = "/pay/del/{id}")
    public ResultData deletePay(@PathVariable("id") Integer id);

    /**
     * Resilience4j CircuitBreaker 的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id);

    /**
     * Resilience4j Bulkhead 的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id);
}
