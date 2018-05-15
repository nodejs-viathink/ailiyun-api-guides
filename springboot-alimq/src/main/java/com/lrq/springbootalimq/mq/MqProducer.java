package com.lrq.springbootalimq.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.lrq.springbootalimq.config.MqConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MqProducer implements InitializingBean,DisposableBean {

    @Autowired
    private OrderProducer orderProducer;
    @Autowired
    private MqConfig mqconfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("生产者初始化");
        orderProducer.start();
    }

    public void sendMessage(Message message){

        //Message message = new Message(this.topic, this.tag, ("simpleMQProducer tag " + this.tag +" index-" + i).getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        String orderId = mqconfig.getProducerId() + Math.random();
        message.setKey(orderId);
        // 分区顺序消息中区分不同分区的关键字段。
        // 全局顺序消息，该字段可以设置为任意非空字符串
        String shardingKey = String.valueOf("simpleMQProducer-" + mqconfig.getProducerId());
        try {
            SendResult sendResult = orderProducer.send(message, shardingKey);
            assert sendResult != null;
            System.out.println(new Date() + " Send mq message success! Topic is:" + mqconfig.getTopic() + ", tag is: " + mqconfig.getTag() + ", msgKey is: " + message.getKey());
        } catch (ONSClientException e) {
            System.out.println("simpleMQProducer send message failed: " + mqconfig.getTopic());
            //出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("停止-------producer destory");
        orderProducer.shutdown();
    }

}
