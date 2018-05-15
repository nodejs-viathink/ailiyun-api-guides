package com.lrq.springbootalimq.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.lrq.springbootalimq.config.MqConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
//实现InitializingBean的类，为bean提供了初始化方法的方式,在类加载后会调用afterPropertiesSet()方法
//实现DisposableBean接口的类，在类销毁时，会调用destroy()方法
public class MqConsumer implements InitializingBean,DisposableBean {

    @Autowired
    private OrderConsumer orderConsumer;

    @Autowired
    private MqConfig mqConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("消费者初始化");
        System.out.println("消费者初始化完成");
    }

    public void start(){
        orderConsumer.start();
    }

    public void receiveMessage(){
        orderConsumer.subscribe(
            // Message 所属的 Topic
            mqConfig.getTopic(),
            // 订阅指定 Topic 下的 Tags：
            // 1. * 表示订阅所有消息
            // 2. TagA || TagB || TagC 表示订阅 TagA 或 TagC 或 TagD 的消息
            mqConfig.getTag(),
        new MessageOrderListener() {
            /**
             * 1. 消息消费处理失败或者处理出现异常，返回 OrderAction.Suspend<br>
             * 2. 消息处理成功，返回与返回 OrderAction.Success
             */
            @Override
            public OrderAction consume(Message message, ConsumeOrderContext context) {
                System.out.println(new Date() + "Consumer " + mqConfig.getConsumerId() +" receive message, topic is:" +
                        message.getTopic() + ", tag is : " + message.getTag() + ", msgKey is:" + message.getKey());
                return OrderAction.Success;
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        orderConsumer.shutdown();
        System.out.println("停止");
    }


}
