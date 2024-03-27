package com.wl.cloud.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * 设置feign 重试
     * @return
     */
    @Bean
    public Retryer myRetryer() {

        //最大请求次数为3(1+2)，初始间隔时间为100ms，重试间最大间隔时间为1s
        return new Retryer.Default(100,1,3);
//        return Retryer.NEVER_RETRY; // 不使用重试
    }


    /**
     * 设置feign 日志级别
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel(){

        return Logger.Level.FULL;
    }
}
