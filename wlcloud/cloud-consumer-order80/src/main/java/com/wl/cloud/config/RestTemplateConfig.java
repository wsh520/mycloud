package com.wl.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced // 添加负载均衡,不然出现 ,UNKNOWN_HOST_EXCEPTION
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

}
