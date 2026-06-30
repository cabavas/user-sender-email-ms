package com.example.user.producer;

import com.example.user.domain.User;
import com.example.user.dto.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component  // Use @Component em vez de @Configuration
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishEvent(User user) {
        var emailDto = new EmailDto(
                user.getId(),
                user.getEmail(),
                "Welcome To My App",
                "Hello " + user.getName() + " welcome to My App"
        );
        rabbitTemplate.convertAndSend("", "email-queue", emailDto);
    }
}