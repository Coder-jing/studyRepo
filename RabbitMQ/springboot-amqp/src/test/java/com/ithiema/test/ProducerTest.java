package com.ithiema.test;

import com.ithiema.rabbitmq.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    private RabbitTemplate template;

    @Test
    public void testSend(){
        template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"boot.haha","hello_springboot_sendMessage");
    }
}
