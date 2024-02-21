package com.alibaba.nacos.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.alibaba.nacos.example.dao")
public class NacosClientApplication {
    public static void main(String[] args) {
        //看这里，加上这句话
        System.setProperty("es.set.netty.runtime.available.processors","false");

        SpringApplication.run(NacosClientApplication.class, args);
    }


    /**
     * @LoadBalanced 开启负载均衡
     * 注册一个实例的RestTemplate bean
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
