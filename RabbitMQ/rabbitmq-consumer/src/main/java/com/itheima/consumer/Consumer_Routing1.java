package com.itheima.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Routing1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //配置连接信息
        factory.setHost("192.168.245.129");
        factory.setPort(5672);
        factory.setVirtualHost("itcast");
        factory.setUsername("heima");
        factory.setPassword("heima");
        //获取连接
        Connection connection = factory.newConnection();
        //创建Channel
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare();
        //接收消息
        /*
            basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback)
            1.queue:队列名称
            2.autoAck:是否自动确认
            3.callback：回调对象
         */
        com.rabbitmq.client.Consumer consumer =new DefaultConsumer(channel){
            /**
             * 回调方法，当收到消息后，会自动执行该方法
             * @param consumerTag 消息标志
             * @param envelope 获取一些信息，交换机，路由key
             * @param properties 配置信息
             * @param body 真实的数据
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("consumerTag:"+consumerTag);
                System.out.println("exchange:"+envelope.getExchange());
                System.out.println("routeKey:"+envelope.getRoutingKey());
                System.out.println("properties:"+properties);
                System.out.println("将日志信息保存到数据库");
                System.out.println("body:"+new String(body));
            }
        };
        channel.basicConsume("test_direct_queue1",true,consumer);
    }
}
