package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AppointmentsEntity;
import com.kmgeronimo.dentalclinicbackend.entity.PaymentEntity;
import com.kmgeronimo.dentalclinicbackend.repository.AppointmentRepository;
import com.kmgeronimo.dentalclinicbackend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<PaymentEntity> fetchAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentEntity fetchPayment(String id) {
        AppointmentsEntity appointmentsEntity = appointmentRepository.findById(id).get();
        PaymentEntity paymentEntity = paymentRepository.findByAppointment(appointmentsEntity);
        return paymentEntity;
    }
}
