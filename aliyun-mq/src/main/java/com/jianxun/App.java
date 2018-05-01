package com.jianxun;

import com.jianxun.consumer.SimpleMQConsumerRunnable;
import com.jianxun.producer.SimpleMQProducerRunnable;

public class App
{
    public static void main( String[] args ) {
        String tag1 = "order";
        String tag2 = "logistics";
        // 先启动消费者
        SimpleMQConsumerRunnable consumer1 = new SimpleMQConsumerRunnable(MQConfig.TOPIC, tag1, MQConfig.CONSUMER1_ID, MQConfig.ACCESS_KEY, MQConfig.SECRET_KEY, MQConfig.ONSADDR);
        SimpleMQConsumerRunnable consumer2 = new SimpleMQConsumerRunnable(MQConfig.TOPIC, tag1 + "||" + tag2, MQConfig.CONSUMER2_ID, MQConfig.ACCESS_KEY, MQConfig.SECRET_KEY, MQConfig.ONSADDR);
        new Thread(consumer1).start();
        new Thread(consumer2).start();
        // 启动生产者
        SimpleMQProducerRunnable producer1 = new SimpleMQProducerRunnable(MQConfig.TOPIC, tag1, MQConfig.PRODUCER_ID, MQConfig.ACCESS_KEY, MQConfig.SECRET_KEY, MQConfig.ONSADDR);
        SimpleMQProducerRunnable producer2 = new SimpleMQProducerRunnable(MQConfig.TOPIC, tag2, MQConfig.PRODUCER_ID, MQConfig.ACCESS_KEY, MQConfig.SECRET_KEY, MQConfig.ONSADDR);
        new Thread(producer1).start();
        new Thread(producer2).start();
    }
}
