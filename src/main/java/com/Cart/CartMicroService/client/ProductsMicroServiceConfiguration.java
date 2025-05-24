package com.Cart.CartMicroService.client;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableFeignClients(basePackages = "com.ryszardpanda.wiremockfortesting.proxy")
public class ProductsMicroServiceConfiguration {

    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(100, 500, 10);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
