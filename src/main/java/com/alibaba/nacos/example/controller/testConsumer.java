package com.alibaba.nacos.example.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
public class testConsumer {
    Logger logger= LoggerFactory.getLogger(testConsumer.class);

    @Autowired
    RestTemplate restTemplate;

    /**
     * 注入DiscoveryClient
     */
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/hi")
    public String hiResttemplate(){
        //根据服务名获取 服务内的实例
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-client");
        for (ServiceInstance instance : instances) {
            System.out.println(JSONObject.toJSONString(instance));
        }
        if(instances.size() > 0){
            String host = instances.get(0).getHost();
            URI uri = instances.get(0).getUri();
            int port = instances.get(0).getPort();
            System.out.println("Host: " + host);
            System.out.println("URI: " + uri);
            System.out.println("port: " + port);
            String instanceId = instances.get(0).getServiceId();
            System.out.println("instanceId : " + instanceId);
            return restTemplate.getForObject(uri + "/" + "name",String.class);
        }
        return "";
    }
}
