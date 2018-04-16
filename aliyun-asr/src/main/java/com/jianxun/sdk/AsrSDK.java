package com.jianxun.sdk;

import com.alibaba.idst.nls.NlsClient;
import com.alibaba.idst.nls.NlsFuture;
import com.alibaba.idst.nls.event.NlsEvent;
import com.alibaba.idst.nls.event.NlsListener;
import com.alibaba.idst.nls.protocol.NlsRequest;
import com.alibaba.idst.nls.protocol.NlsResponse;

import java.io.InputStream;

public class AsrSDK implements NlsListener {
    private String accessKey;
    private String accessSecret;
    private static NlsClient client = new NlsClient();
    public AsrSDK(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        // 初始化NlsClient
        client.init();
    }

    public void shutDown() {
        System.out.println("close NLS client");
        // 关闭客户端并释放资源
        client.close();
        System.out.println("demo done");
    }

    public void startAsr() {
        //开始发送语音
        System.out.println("open audio file...");
        InputStream fis = null;
        try {
            fis = this.getClass().getClassLoader().getResourceAsStream("sample.pcm");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fis != null) {
            System.out.println("create NLS future");
            try {
                NlsRequest req = new NlsRequest();
                req.setAppKey("nls-service");
                req.setAsrFormat("pcm"); // 设置语音文件格式为pcm,支持16k 16bit 的无头的pcm文件。

                req.authorize(this.accessKey, this.accessSecret); // 用户的Access Key ID和Access Key
                // Secret
                NlsFuture future = client.createNlsFuture(req, this); // 实例化请求,传入请求和监听器
                System.out.println("call NLS service");
                byte[] b = new byte[8000];
                int len = 0;
                while ((len = fis.read(b)) > 0) {
                    future.sendVoice(b, 0, len); // 发送语音数据
                    Thread.sleep(50);
                }
                future.sendFinishSignal(); // 语音识别结束时，发送结束符
                System.out.println("main thread enter waiting for less than 10s.");
                future.await(10000); // 设置服务端结果返回的超时时间
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("calling NLS service end");
        }
    }

    @Override
    public void onMessageReceived(NlsEvent nlsEvent) {
        //识别结果的回调
        NlsResponse response = nlsEvent.getResponse();
        String result = "";
        int statusCode = response.getStatus_code();
        if (response.getAsr_ret() != null) {
            result += response.getAsr_ret();
        }
        if (result != null) {
            System.out.println("statusCode：" + statusCode);
            System.out.println("结果： " + result);
        } else {
            System.out.println("error：" + response.jsonResults.toString());
        }
    }

    @Override
    public void onOperationFailed(NlsEvent nlsEvent) {
        //识别失败的回调
        String result = "";
        result += "on operation failed: statusCode=[" + nlsEvent.getResponse().getStatus_code() + "], " + nlsEvent.getErrorMessage();
        System.out.println(result);
    }

    @Override
    public void onChannelClosed(NlsEvent nlsEvent) {
        //socket 连接关闭的回调
        System.out.println("on websocket closed.");
    }
}
