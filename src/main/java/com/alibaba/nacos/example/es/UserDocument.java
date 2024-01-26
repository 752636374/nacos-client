package com.alibaba.nacos.example.es;

import lombok.Data;

@Data
public class UserDocument {
    private String id;
    private String name;
    private String sex;
    private Integer age;
    private String city;
    // 省略 getter/setter
}