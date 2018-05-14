package com.example.mybatis.mapper;

import com.example.mybatis.entity.UserEntity;
import com.example.mybatis.param.UserParam;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    void addUser(UserEntity userEntity);
    void deleteUser(Long id);
    void updateUser(UserEntity userEntity);
    UserEntity getOne(Long id);
    List<UserEntity> getAll();
    List<UserEntity> getList(UserParam userParam);
    int getCount(UserParam userParam);
    List<UserEntity> findByPage(UserParam userParam);
}
