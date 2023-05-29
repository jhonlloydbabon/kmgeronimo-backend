package com.kmgeronimo.dentalclinicbackend.repository;

import com.kmgeronimo.dentalclinicbackend.entity.AppointmentsEntity;
import com.kmgeronimo.dentalclinicbackend.entity.InsuranceEntity;
import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String> {
    PatientEntity findByContactNumberAndEmail(String phoneNumber, String email);

    PatientEntity findByEmail(String email);

    PatientEntity findByContactNumber(String contactNumber);
    InsuranceEntity findByInsurance(String id);

    PatientEntity findByUsername(String username);
}
