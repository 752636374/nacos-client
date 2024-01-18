package com.alibaba.nacos.example.controller.consumer;


import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Example of remote invocation of service fusing and load balancing.
 *
 * @author xiaojing, fangjian0423, MieAh
 */
@RestController
public class TestController {

    @Resource
    private RestTemplate urlCleanedRestTemplate;

    @Resource
    private RestTemplate restTemplate;


    @Resource
    private DiscoveryClient discoveryClient;

    private static final String SERVICE_PROVIDER_ADDRESS = "http://nacos-client";

    @GetMapping("/hi1")
    public String hi1() {
        return restTemplate.getForObject(SERVICE_PROVIDER_ADDRESS + "/name",
                String.class);
    }

    @GetMapping("/services/{service}")
    public Object client(@PathVariable String service) {
        return discoveryClient.getInstances(service);
    }


    @GetMapping("/services")
    public Object services() {
        return discoveryClient.getServices();
    }

}
