package com.kmgeronimo.dentalclinicbackend.repository;

import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, String> {
    PatientEntity findByPhoneNumberAndEmail(String phoneNumber, String email);
}
