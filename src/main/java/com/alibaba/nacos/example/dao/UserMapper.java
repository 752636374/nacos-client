package com.alibaba.nacos.example.dao;

import com.alibaba.nacos.example.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getDeptById(@Param("id") Long id);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteDeptById(@Param("id") Long id);

//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @Insert("INSERT INTO user(name,age) VALUES(#{name},#{age})")
    int insertDept(User User);

    @Update("UPDATE user SET name = #{name} WHERE id = #{id}")
    int updateDept(User User);
}
