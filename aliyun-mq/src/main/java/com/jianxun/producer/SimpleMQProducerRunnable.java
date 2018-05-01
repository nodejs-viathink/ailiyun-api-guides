package com.jianxun.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import lombok.Data;


import java.util.Date;
import java.util.Properties;

@Data
public class SimpleMQProducerRunnable implements Runnable{
    private String topic;
    private String tag;
    private String producerId;
    private String accessKey;
    private String secretKey;
    private String onsAddr;
    public SimpleMQProducerRunnable(String topic, String tag, String producerId, String accessKey, String secretKey, String onsAddr) {
        this.topic = topic;
        this.tag = tag;
        this.producerId = producerId;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.onsAddr = onsAddr;
    }
    @Override
    public void run() {
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.ProducerId, this.producerId);
        producerProperties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        producerProperties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, this.onsAddr);
        // 发送超时时间
        producerProperties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        OrderProducer producer = ONSFactory.createOrderProducer(producerProperties);
        producer.start();
        System.out.println("SimpleMQProducer" + this.producerId + "started...");
        for (int i = 0; i < 10; i++) {
            Message message = new Message(this.topic, this.tag, "simpleMQProducer message test".getBytes());
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            String orderId = this.producerId + "-" + Math.random();
            message.setKey(orderId);
            // 分区顺序消息中区分不同分区的关键字段。
            // 全局顺序消息，该字段可以设置为任意非空字符串。
            String shardingKey = String.valueOf("simpleMQProducer-" + producerId);
            try {
                SendResult sendResult = producer.send(message, shardingKey);
                assert sendResult != null;
                System.out.println(new Date() + " Send mq message success! Topic is:" + this.topic + " tag is: " + this.tag + "msgId is: " + sendResult.getMessageId());
            } catch (ONSClientException e) {
                System.out.println("simpleMQProducer send message failed: " + this.topic);
                //出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}
