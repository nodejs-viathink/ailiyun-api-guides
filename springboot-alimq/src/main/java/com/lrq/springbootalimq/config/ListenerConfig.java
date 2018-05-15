package com.lrq.springbootalimq.config;

import com.lrq.springbootalimq.mq.MqConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
//Spring Boot 启动加载数据
@Order(value=1)
public class ListenerConfig implements CommandLineRunner {

    @Autowired
    private MqConsumer mqConsumer;

    @Override
    public void run(String... strings){
        System.out.println("开始消费");
        mqConsumer.start();
        mqConsumer.receiveMessage();
    }
}

