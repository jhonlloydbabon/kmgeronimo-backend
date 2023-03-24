package com.kmgeronimo.dentalclinicbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageModel {

    private String senderName;

    private String recipientName;

    private String message;

    private LocalDateTime timestamp;

    private Status status;
}
