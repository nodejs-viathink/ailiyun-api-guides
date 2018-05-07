package com.jianxun.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        Connection connection = factory.newConnection();  //创建连接
        final Channel channel = connection.createChannel(); //创建信道
        String exchangeName = "robot-exchange2";
        channel.exchangeDeclare(exchangeName, "direct", true);
        String queueName = "robot-queue2";
        channel.queueDeclare(queueName, true, false, false, null);
        String routingKey = "robot-key2";
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicQos(1); //设置客户端最多接收未确认消息个数,当消费者确认后RabbitMQ才会继续向该消费者发送消息
        boolean autoAck = false; //设置为false,RabbitMQ会等待消费者确认消费后才移除消息
        //接收消息

//        //拉模式 单条获取消息
//        GetResponse response = channel.basicGet(queueName,autoAck);
//        System.out.println(new String(response.getBody()));
//        channel.basicAck(response.getEnvelope().getDeliveryTag(),false);

        //推模式，持续订阅，不断推送
        channel.basicConsume(queueName, autoAck, "robot-consumer2", new DefaultConsumer(channel) {
            @Override
            //Broker 向消费者推送消息,处理RabbitMQ推送过来的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag: " + consumerTag + ", deliveryTag: " + envelope.getDeliveryTag() + ", body:" + new String(body) );
                //消费后向Broker发送确认消息
                channel.basicAck(envelope.getDeliveryTag(), false);
                // channel.basicReject(envelope.getDeliveryTag(), false); // 第二个参数设置成true会被重新入队
            }
        });
    }
}
