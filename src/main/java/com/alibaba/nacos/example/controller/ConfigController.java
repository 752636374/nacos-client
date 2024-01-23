package com.alibaba.nacos.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.example.controller.mq.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RefreshScope
@Slf4j
public class ConfigController {

    @Value(value = "${useLocalCache:false}")
    private boolean useLocalCache;

    @Value(value = "${name:1}")
    private String name;

    @Autowired
    private MQProducerService mqProducerService;

    /**
     * http://localhost:8080/config/get
     */
    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }


    @RequestMapping
    public String name() {
        return name;
    }

    @RequestMapping("/send")
    public String sendMsg(@PathParam("msg") String msg) {
        SendResult sendResult = mqProducerService.sendMsg(msg);
        return JSON.toJSONString(sendResult);
    }
}