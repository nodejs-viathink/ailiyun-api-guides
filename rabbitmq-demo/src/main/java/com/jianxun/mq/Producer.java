package com.jianxun.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "robot-exchange2";
        channel.exchangeDeclare(exchangeName, "direct", true);
        String queueName = "robot-queue2";
        channel.queueDeclare(queueName, true, false, false, null);
        String routingKey = "robot-key2";
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.confirmSelect();
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
        long nextSeqNo = channel.getNextPublishSeqNo();
        String message = "你好";
        channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("nextSeqNo: " + nextSeqNo );
    }
}
