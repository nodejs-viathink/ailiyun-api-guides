package com.example.mybatis.controller;

import com.example.mybatis.entity.UserEntity;
import com.example.mybatis.param.UserParam;
import com.example.mybatis.result.Page;
import com.example.mybatis.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    //@Value获取application.properties配置中的属性
    @Value("${com.example.mybatis.name}")
    private String myname;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String saveUser(UserEntity userEntity){
        String data = userService.saveUser(userEntity);
        System.out.print(data);
        return data;
    }

    @PostMapping("/delete")
    public String deleteUser(Long id){
        return userService.deleteUserById(id);
    }

    @PostMapping("/update")
    public String updateUser(UserEntity userEntity){
        return userService.updateUserById(userEntity);
    }

    @GetMapping("/search/{id}")
    public UserEntity findUserById(@PathVariable Long id){
        UserEntity userEntity = userService.findUserById(id);
        System.out.print(userEntity.getUserSex());
        System.out.print(userEntity.getUserName());
        return userEntity;
    }

    @GetMapping("/search/users")
    public List<UserEntity> findAllUsers(){
        System.out.print(myname);
        return userService.getUserList();
    }

    @GetMapping("/search/pages/users")
    public Page<UserEntity> searchUserByPage(UserParam userParam){
        List<UserEntity> users =  userService.userListByPage(userParam);
        int sum = userService.countUsers(userParam);
        //构造返回结果类
        Page page = new Page(userParam.getCurrentPage(),userParam.getPageSize(),sum,users);
        return page;
    }

    @GetMapping("/page/help/search")
    public PageInfo<UserEntity> getAll(UserParam userParam) {
        List<UserEntity> userList = userService.findUserByPage(userParam);
        //分页的包装类PageInfo,用PageInfo对结果进行包装
        return new PageInfo(userList);
    }


}
