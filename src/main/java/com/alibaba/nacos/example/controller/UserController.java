package com.alibaba.nacos.example.controller;

import com.alibaba.nacos.example.constants.ResultConstants;
import com.alibaba.nacos.example.dao.UserMapper;
import com.alibaba.nacos.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/add")
    public String add(@RequestBody User user){
        userMapper.insertDept(user);
        return ResultConstants.SUCCESS;
    }

    @RequestMapping("/select")
    public User select(@PathParam("id")Long id){
       return userMapper.getDeptById(id);
    }
}
