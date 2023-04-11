package com.kmgeronimo.dentalclinicbackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class History {
    private String name;
    private LocalDate appointmentDate;
    private String description;
    private String status;
}
