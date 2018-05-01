package com.jianxun.ram;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jianxun.MQConfig;

public class AcsClientFactory {
    public static DefaultAcsClient getRamAcsClient() {
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "RAM", "ram.aliyuncs.com");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                MQConfig.ACCESS_KEY,
                MQConfig.SECRET_KEY);
        return new DefaultAcsClient(profile);
    }
    public static DefaultAcsClient getMQAcsClient() {
        try {
            DefaultProfile.addEndpoint("cn-qingdao", "cn-qingdao", "Ons", "ons.cn-qingdao.aliyuncs.com");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IClientProfile profile = DefaultProfile.getProfile("cn-qingdao",
                MQConfig.ACCESS_KEY,
                MQConfig.SECRET_KEY);
        return new DefaultAcsClient(profile);
    }
}
