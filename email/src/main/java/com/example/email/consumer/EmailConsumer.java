package com.example.email.consumer;

import com.example.email.domain.Email;
import com.example.email.dto.EmailDto;
import com.example.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email-queue")
    public void listenEmailQueue(@Payload EmailDto emailDto) {
        System.out.println(emailDto);
        var email = new Email();
        BeanUtils.copyProperties(emailDto, email);
        email.setSender(emailDto.recipient());
        email.setSentAt(java.time.LocalDateTime.now());
        email.setUserId(emailDto.id());
        emailService.sendEmail(email);
    }

}
