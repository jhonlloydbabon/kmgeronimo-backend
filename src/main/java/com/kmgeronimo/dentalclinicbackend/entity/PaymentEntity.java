package com.kmgeronimo.dentalclinicbackend.entity;


import com.kmgeronimo.dentalclinicbackend.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String paymentId;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    private String method;
    private Double totalPayment;
    private String type;

    @Lob
    private String paymentPhoto;
    private PaymentStatus status;
}