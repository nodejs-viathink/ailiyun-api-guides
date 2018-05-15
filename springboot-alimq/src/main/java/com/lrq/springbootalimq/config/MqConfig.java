package com.lrq.springbootalimq.config;


import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Data
@Configuration
public class MqConfig {

    @Value("${mq.config.myProviderId}")
    public String producerId;

    @Value("${mq.config.myConsumerId1}")
    public String consumerId;

    @Value("${mq.config.AccessKey}")
    public String accessKey;

    @Value("${mq.config.SecretKey}")
    public String secretKey;

    @Value("${mq.config.myTopIc}")
    public String topic;

    @Value("${mq.config.tag1}")
    public String tag;

    @Value("${mq.config.ONSAddr}")
    public String onsAddr;

    @Value("${mq.config.sendMsgTimeoutMillis}")
    public String sendMsgTimeoutMillis;

    @Value("${mq.config.suspendTimeMillis}")
    public String suspendTimeMillis;

    @Value("${mq.config.maxReconsumeTimes}")
    public String maxReconsumeTimes;

    //消费者的配置,创建消费者
    @Bean
    public OrderConsumer orderConsumer(){
        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(PropertyKeyConst.ConsumerId, consumerId);
        consumerProperties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        consumerProperties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        consumerProperties.setProperty(PropertyKeyConst.ONSAddr, onsAddr);
        // 顺序消息消费失败进行重试前的等待时间 单位(毫秒)
        consumerProperties.put(PropertyKeyConst.SuspendTimeMillis, suspendTimeMillis);
        // 消息消费失败时的最大重试次数
        consumerProperties.put(PropertyKeyConst.MaxReconsumeTimes, maxReconsumeTimes);
        OrderConsumer consumer = ONSFactory.createOrderedConsumer(consumerProperties);
        return consumer;
    }
    //生产者的配置，创建生产者
    @Bean
    public OrderProducer orderProducer (){
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.ProducerId, producerId);
        producerProperties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        producerProperties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        producerProperties.setProperty(PropertyKeyConst.ONSAddr, onsAddr);
        producerProperties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, sendMsgTimeoutMillis);
        OrderProducer producer = ONSFactory.createOrderProducer(producerProperties);
        return producer;
    }

}
