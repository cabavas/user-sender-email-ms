package com.example.user.dto;

import java.util.UUID;

public record EmailDto(
        UUID id,
        String recipient,
        String subject,
        String body
) {
}
