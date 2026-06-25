package com.example.email.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMq {

    public Queue queue() {
        String queueName = "email-queue";
        return new Queue(queueName, true);
    }

}
