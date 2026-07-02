package com.example.email.service;

import com.example.email.domain.Email;
import com.example.email.enums.EmailStatus;
import com.example.email.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Value("${EMAIL_USERNAME}")
    private String sender;

    public void sendEmail(Email email) {
        // Verifica se já existe (idempotência)
        if (email.getId() != null && emailRepository.existsById(email.getId())) {
            System.out.println("Email com ID " + email.getId() + " já processado. Ignorando.");
            return;
        }

        // Define status inicial e salva antes de enviar
        email.setStatus(EmailStatus.PENDING);
        try {
            emailRepository.save(email);
            System.out.println("Email salvo com status PENDING. ID: " + email.getId());
        } catch (Exception e) {
            // Se falhar ao salvar (ex: conflito de ID), assume que já foi processado
            System.out.println("Falha ao salvar email (provavelmente já existe): " + e.getMessage());
            return; // Não envia
        }

        // Agora tenta enviar
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(email.getRecipient());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            mailSender.send(message);
            email.setStatus(EmailStatus.SENT);
            System.out.println("E-mail enviado com sucesso para " + email.getRecipient());
        } catch (Exception e) {
            System.out.println("Falha ao enviar e-mail: " + e);
            email.setStatus(EmailStatus.FAILED);
        }

        try {
            emailRepository.save(email);
            System.out.println("Status atualizado para: " + email.getStatus());
        } catch (Exception e) {
            System.out.println("Erro ao atualizar status: " + e);
        }
    }
}
