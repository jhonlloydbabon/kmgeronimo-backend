package com.kmgeronimo.dentalclinicbackend.repository;

import com.kmgeronimo.dentalclinicbackend.entity.AppointmentsEntity;
import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import com.kmgeronimo.dentalclinicbackend.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    PaymentEntity findByAppointment(AppointmentsEntity appointment);
}
