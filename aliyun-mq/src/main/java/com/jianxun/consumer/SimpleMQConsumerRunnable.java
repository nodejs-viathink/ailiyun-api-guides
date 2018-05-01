package com.jianxun.consumer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import lombok.Data;

import java.util.Date;
import java.util.Properties;

@Data
public class SimpleMQConsumerRunnable implements Runnable{
    private String topic;
    private String tag;
    private String consumerId;
    private String accessKey;
    private String secretKey;
    private String onsAddr;

    public SimpleMQConsumerRunnable(String topic, String tag, String consumerId, String accessKey, String secretKey, String onsAddr) {
        this.topic = topic;
        this.tag = tag;
        this.consumerId = consumerId;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.onsAddr = onsAddr;
    }

    @Override
    public void run() {
        Properties properties = new Properties();
        // 您在控制台创建的 Consumer ID
        properties.put(PropertyKeyConst.ConsumerId, this.consumerId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, this.accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, this.secretKey);
        // 设置 TCP 接入域名（此处以公共云生产环境为例）
        properties.put(PropertyKeyConst.ONSAddr, this.onsAddr);
        // 顺序消息消费失败进行重试前的等待时间 单位(毫秒)
        properties.put(PropertyKeyConst.SuspendTimeMillis, "3000");
        // 消息消费失败时的最大重试次数
        properties.put(PropertyKeyConst.MaxReconsumeTimes, "10");
        // 在订阅消息前，必须调用 start 方法来启动 Consumer，只需调用一次即可。
        OrderConsumer consumer = ONSFactory.createOrderedConsumer(properties);
        consumer.subscribe(
                // Message 所属的 Topic
                this.topic,
                // 订阅指定 Topic 下的 Tags：
                // 1. * 表示订阅所有消息
                // 2. TagA || TagB || TagC 表示订阅 TagA 或 TagC 或 TagD 的消息
                this.tag,
                new MessageOrderListener() {
                    /**
                     * 1. 消息消费处理失败或者处理出现异常，返回 OrderAction.Suspend<br>
                     * 2. 消息处理成功，返回与返回 OrderAction.Success
                     */
                    @Override
                    public OrderAction consume(Message message, ConsumeOrderContext context) {
                        System.out.println(new Date() + "Consumer " + consumerId +" receive message, topic is:" +
                                message.getTopic() + ", tag is : " + message.getTag() + ", msgKey is:" + message.getKey());
                        return OrderAction.Success;
                    }
                });
        consumer.start();
        System.out.println("consumer id: " + this.consumerId + " start...");
    }
}
