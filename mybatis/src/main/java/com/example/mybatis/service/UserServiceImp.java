package com.example.mybatis.service;

import com.example.mybatis.entity.UserEntity;
import com.example.mybatis.mapper.UserMapper;
import com.example.mybatis.param.UserParam;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public String saveUser(UserEntity userEntity){
        userMapper.addUser(userEntity);
        return "save success";
    }

    @Override
    public String deleteUserById(Long id) {
        userMapper.deleteUser(id);
        return "delete success";
    }

    @Override
    public String updateUserById(UserEntity userEntity) {
        userMapper.updateUser(userEntity);
        return "update success";
    }

    @Override
    public UserEntity findUserById(Long id) {
        UserEntity userEntity = userMapper.getOne(id);
        return userEntity;
    }

    @Override
    public List<UserEntity> getUserList() {
        List<UserEntity>  users =  userMapper.getAll();
        return users;
    }

    @Override
    public List<UserEntity> userListByPage(UserParam userParam) {
        return userMapper.getList(userParam);
    }

    @Override
    public int countUsers(UserParam userParam) {
        return userMapper.getCount(userParam);
    }

    @Override
    public List<UserEntity> findUserByPage(UserParam userParam) {
        //在需要进行分页的 MyBatis 查询方法前调用 PageHelper.startPage 静态方法即可，紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页
        PageHelper.startPage(userParam.getCurrentPage(), userParam.getPageSize());
        return userMapper.findByPage(userParam);
    }

}
