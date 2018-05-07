package com.jianxun.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();  //创建连接
        Channel channel = connection.createChannel();  //创建信道
        String exchangeName = "robot-exchange2";  //交换器名称
        channel.exchangeDeclare(exchangeName, "direct", true); //创建一个direct 类型的交换器
        String queueName = "robot-queue2"; //队列名称
        channel.queueDeclare(queueName, true, false, false, null); //创建一个队列
        String routingKey = "robot-key2";
        channel.queueBind(queueName, exchangeName, routingKey); //通过routingKey将交换器和队列绑定
        //开启发送端确认模式-消息确认机制
        channel.confirmSelect();
        //异步 confirm 方法
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("收到消息确认回调, deliveryTag: " + deliveryTag + ",multiple: " + multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("收到消息丢失回调, deliveryTag: " + deliveryTag + ",multiple: " + multiple);
            }
        });
        while(true){
            long nextSeqNo = channel.getNextPublishSeqNo();
            String message = "你好";
            //String message2 = "hello";
            //发送消息channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

            //channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message2.getBytes());
            System.out.println("nextSeqNo: " + nextSeqNo );
        }

    }
}
