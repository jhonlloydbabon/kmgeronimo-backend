package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.PaymentEntity;

import java.util.List;

public interface PaymentService {
    List<PaymentEntity> fetchAllPayment();

    PaymentEntity fetchPayment(String id);
}
