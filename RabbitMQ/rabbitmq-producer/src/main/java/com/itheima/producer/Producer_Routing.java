package com.itheima.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送消息
 */
public class Producer_Routing {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
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
        //5.创建交换机
        /*
            exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
            1.exchange：交换机名称
            2.type：交换机类型
                DIRECT：定向
                FANOUT：扇形，广播，发送消息到每一个与之绑定的队列
                TOPIC：通配符的方式
                HEADERS：参数匹配
            3.durable：是否持久化
            4.autoDelete：是否自动删除
            5.internal：内部使用，一般为false
            6.arguments:参数
         */
        String exchange="test_direct";
        channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT,true,false,false,null);
        //6.创建队列
        String queue1="test_direct_queue1";
        String queue2="test_direct_queue2";
        channel.queueDeclare(queue1,true,false,false,null);
        channel.queueDeclare(queue2,true,false,false,null);
        //7.绑定队列和交换机
        /*
            queueBind(String queue, String exchange, String routingKey)
            1.queue:队列名称
            2.exchange：交换机名成
            3。routingKey：路由键，绑定规则
                如果交换机为fanout，toutingKey为”“
            3.arguments：
         */
        channel.queueBind(queue1,exchange,"error");
        channel.queueBind(queue2,exchange,"error");
        channel.queueBind(queue2,exchange,"info");
        channel.queueBind(queue2,exchange,"warning");
        //8.发送消息
        String body="日志信息：张三调用了delete（）方法。。。出错了。。。日志级别：error...";
//        channel.basicPublish(exchange,"error",null,body.getBytes());
        channel.basicPublish(exchange,"info",null,body.getBytes());
        //9.释放资源
        channel.close();
        connection.close();
    }
}
