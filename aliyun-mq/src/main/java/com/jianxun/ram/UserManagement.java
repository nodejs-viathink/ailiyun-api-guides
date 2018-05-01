package com.jianxun.ram;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.ram.model.v20150501.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserManagement {

    private DefaultAcsClient client;
    private String username = "jianxun-test-user2";

    @Before
    public void init() {
        client = AcsClientFactory.getRamAcsClient();
    }

    @Test
    public void createUser() {
        //设置参数
        CreateUserRequest request = new CreateUserRequest();
        request.setUserName(username);
        request.setComments(username);

        // 发起请求
        try {
            CreateUserResponse response = client.getAcsResponse(request);
            System.out.println("UserId: " + response.getUser().getUserId());
            System.out.println("UserName: " + response.getUser().getUserName());
            System.out.println("Comments: " + response.getUser().getComments());
            System.out.println("CreateTime: " + response.getUser().getCreateDate());
        } catch (ClientException e) {
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            e.printStackTrace();
        }
    }

    @Test
    public void getUserList() {
        ListUsersRequest request = new ListUsersRequest();
        try {
            ListUsersResponse response = client.getAcsResponse(request);
            List<ListUsersResponse.User> list = response.getUsers();
            for (ListUsersResponse.User user : list) {
                System.out.println("UserId: " + user.getUserId());
                System.out.println("UserName: " + user.getUserName());
                System.out.println("Comments: " + user.getComments());
                System.out.println("CreateTime: " + user.getCreateDate());
                System.out.println("--------------------------------");
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一个子用户可以创建多个accessKey,我们创建一个就行，需要判断，如果有了就返回老的，
     * 不需要创建新的
     */
    @Test
    public void createAccessKey() {
        CreateAccessKeyRequest request = new CreateAccessKeyRequest();
        request.setUserName(username);
        try {
            CreateAccessKeyResponse response = client.getAcsResponse(request);
            System.out.println("AccessKeyId: " + response.getAccessKey().getAccessKeyId());
            System.out.println("AccessKeySecret: " + response.getAccessKey().getAccessKeySecret());
            System.out.println("Status: " + response.getAccessKey().getStatus());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
