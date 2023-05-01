package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.PaymentEntity;
import com.kmgeronimo.dentalclinicbackend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/")
    public List<PaymentEntity> fetchAllPayment(){
        return paymentService.fetchAllPayment();
    }

    @GetMapping("/{id}")
    public PaymentEntity fetchPayment(@PathVariable("id") String id){ return paymentService.fetchPayment(id); }
}
