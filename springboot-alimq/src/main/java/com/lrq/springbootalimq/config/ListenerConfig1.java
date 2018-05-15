package com.lrq.springbootalimq.config;


import com.aliyun.openservices.ons.api.Message;
import com.lrq.springbootalimq.mq.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=2)
public class ListenerConfig1 implements CommandLineRunner {

    @Autowired
    private MqProducer mqProducer;
    @Autowired
    private MqConfig mqConfig;

    @Override
    public void run(String... strings){
        System.out.println("开始生产");
        String msgbody = "开始产生订单消息";
        Message message = new Message(
                // Message 所属的 Topic
                mqConfig.getTopic(),
                // Message Tag,
                // 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                mqConfig.getTag(),
                // Message Body
                // 任何二进制形式的数据，MQ 不做任何干预，需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                msgbody.getBytes());
        mqProducer.sendMessage(message);
    }
}
