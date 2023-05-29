package com.kmgeronimo.dentalclinicbackend.repository;

import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import com.kmgeronimo.dentalclinicbackend.entity.PatientVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientVerificationTokenRepository extends JpaRepository<PatientVerificationToken, String> {
    PatientVerificationToken findByPatientEntity(PatientEntity patientEntity);
    PatientVerificationToken findByToken(String token);
}
