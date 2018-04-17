package com.jianxun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jianxun.http.HttpResponse;
import com.jianxun.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class AppHttp {
    private static Logger logger = LoggerFactory.getLogger(AppHttp.class);
    public static void main(String[] args) throws IOException {
        String url = "http://nlsapi.aliyun.com/recognize?";
        String ak_id = args[0];
        String ak_secret = args[1];
        //使用对应的ASR模型 详情见文档部分2
        String model = "chat";
        url = url+"model="+model;
        //读取本地的语音文件
        Path path = FileSystems.getDefault().getPath("src/main/resources/test.pcm");
        byte[] data = Files.readAllBytes(path);
        HttpResponse response = HttpUtil.sendAsrPost(data,"pcm",16000,url,ak_id,ak_secret);
        logger.info(JSON.toJSONString(response));
        JSONObject jsonObject= JSON.parseObject(response.getResult());
        logger.info(jsonObject.getString("result"));
    }
}
