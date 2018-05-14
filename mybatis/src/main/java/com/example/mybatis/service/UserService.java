package com.example.mybatis.service;

import com.example.mybatis.entity.UserEntity;
import com.example.mybatis.param.UserParam;

import java.util.List;

public interface UserService {
    String saveUser(UserEntity userEntity);
    String deleteUserById(Long id);
    String updateUserById(UserEntity userEntity);
    UserEntity findUserById(Long id);
    List<UserEntity> getUserList();
    List<UserEntity> userListByPage(UserParam userParam);
    int countUsers(UserParam userParam);
    List<UserEntity> findUserByPage(UserParam userParam);
}
