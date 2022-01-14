package com.itheima.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送消息
 */
public class Producer_WorkQueues {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory=new ConnectionFactory();
        //2.设置参数
        connectionFactory.setHost("192.168.245.129");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("itcast");
        connectionFactory.setUsername("heima");
        connectionFactory.setPassword("heima");
        //3.获取连接
        Connection connection = connectionFactory.newConnection();
        //4.创建Channel
        Channel channel = connection.createChannel();
        /*
           queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
            1.queue:队列名称
            2.durable:是否持久化，当mq重启之后，还在
            3.exclusive：是否独占，只有一个消费者能够监听这个队列；当Connection关闭的时候，是否删除队列
            4.autoDelete：是否在自动删除。当没有Consumer是，自动删除掉
            5.arguments：参数
         */
        //5.创建队列
        //如果没有一个叫做helloworld的队列，则会创建一个
        channel.queueDeclare("workQueues",true,false,false,null);
        //6.发送消息
        /*
            basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
            1.exchange:交换机名称，简单模式下交换机会使用默认的，需要设置为""
            2.routingKey：路由名称
            3.props：配置信息，null
            4.body:真实发送的信息
         */
        for (int i = 0; i < 10; i++) {
            String body=i+"helloWorld!";
            channel.basicPublish("","workQueues",null,body.getBytes());
        }
        //6。释放资源
//        channel.close();
//        connection.close();
    }
}
