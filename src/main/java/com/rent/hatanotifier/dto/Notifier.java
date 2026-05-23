package com.rent.hatanotifier.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;


import java.sql.Timestamp;
import java.util.UUID;

public record Notifier(

        @Null
        UUID id,

        String aggregateId,

        @NotBlank(message = "Тип события не может быть пустым")
        String type,

        @JsonRawValue
        @NotNull(message = "Payload не может быть пустым")
        JsonNode payload,

        @Null
        Timestamp createdAt,
        @Null
        Timestamp processedAt
) {
}
