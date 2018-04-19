package com.jianxun.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        String exchangeName = "robot-exchange2";
        channel.exchangeDeclare(exchangeName, "direct", true);
        String queueName = "robot-queue2";
        channel.queueDeclare(queueName, true, false, false, null);
        String routingKey = "robot-key2";
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicQos(1);
        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, "robot-consumer2", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag: " + consumerTag + ", deliveryTag: " + envelope.getDeliveryTag() + ", body:" + new String(body) );
                channel.basicAck(envelope.getDeliveryTag(), false);
                // channel.basicReject(envelope.getDeliveryTag(), false); // 第二个参数设置成true会被重新入队
            }
        });
    }
}
