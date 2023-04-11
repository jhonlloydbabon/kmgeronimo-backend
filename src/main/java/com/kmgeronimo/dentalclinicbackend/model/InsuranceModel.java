package com.kmgeronimo.dentalclinicbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceModel {
    private String card;
    private String company;
    private String cardNumber;
}
