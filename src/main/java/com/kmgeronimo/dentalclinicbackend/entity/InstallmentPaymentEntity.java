package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Data
@Table(name = "installment")
public class InstallmentPaymentEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String scheduleId; //primarykey

    @ManyToOne
    @JoinColumn(name = "paymentId")
    private PaymentEntity payment;

    private Integer installmentNumber;
    private Date dueDate;
    private Double installmentAmount;
    @Lob
    private String installmentPhoto;
}
