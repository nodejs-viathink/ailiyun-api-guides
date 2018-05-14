package com.example.mybatis.param;

import lombok.Data;

//user分页查询条件参数类，继承分页基础类
@Data
public class UserParam extends PageParam{
    private String userName;
    private String userSex;

}
