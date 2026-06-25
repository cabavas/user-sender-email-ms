package com.example.email.domain;

import com.example.email.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "emails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID userId;
    private String recipient;
    private String sender;
    private String subject;
    @Lob
    private String body;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

}
