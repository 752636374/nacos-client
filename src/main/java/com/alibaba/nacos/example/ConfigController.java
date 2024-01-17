package com.alibaba.nacos.example;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {

    @Value(value = "${useLocalCache:false}")
    private boolean useLocalCache;

    @Value(value = "${name:1}")
    private String name;

    /**
     * http://localhost:8080/config/get
     */
    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }


    @RequestMapping
    public String name(){
        return  name;
    }
}