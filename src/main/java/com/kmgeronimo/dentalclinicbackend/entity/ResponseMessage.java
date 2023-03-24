package com.kmgeronimo.dentalclinicbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private HttpStatus status;
    private String message;
}
