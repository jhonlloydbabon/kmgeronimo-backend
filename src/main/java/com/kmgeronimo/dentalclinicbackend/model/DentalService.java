package com.kmgeronimo.dentalclinicbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DentalService {
    private String name;
    private String type;
    private String description;
    private LocalTime duration;
    private Double price;
}
