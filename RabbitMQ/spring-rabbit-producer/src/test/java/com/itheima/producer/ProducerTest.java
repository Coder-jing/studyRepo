package com.itheima.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {

    //1.注入rabbitTempalte

    @Autowired
    private RabbitTemplate template;

    @Test
    public void testHelloWorld(){
        //发送消息
        template.convertAndSend("spring_queue","helloWorld...Spring");
    }

    @Test
    public void testFanout(){
        //发送消息
        template.convertAndSend("spring_fanout_exchange","","fanout...Spring ");
    }

    @Test
    public void testTopic(){
        //发送消息
        template.convertAndSend("spring_topic_exchange","heima.hehe.haha","routingKey...Spring ");
    }
}
