package com.kmgeronimo.dentalclinicbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "insurance")
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String insuranceId;
    private String card;
    private String company;
    private String cardNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "insurance")
    private PaymentEntity payment;
}
