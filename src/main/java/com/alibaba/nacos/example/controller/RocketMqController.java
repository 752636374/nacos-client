package com.alibaba.nacos.example.controller;

import com.alibaba.nacos.example.constants.ResultConstants;
import com.alibaba.nacos.example.mq.SimpleTransationMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yaoheng5
 * @CreateTime: 2024-02-22  20:22
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/rocketMq")
public class RocketMqController {

    @Autowired
    SimpleTransationMessageProducer producer;

    @RequestMapping("sendTransaction")
    public String sendTransaction(String s) {
        producer.send(s);
        return ResultConstants.SUCCESS;
    }
}
