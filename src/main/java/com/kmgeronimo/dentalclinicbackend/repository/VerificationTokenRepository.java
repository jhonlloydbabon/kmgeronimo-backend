package com.kmgeronimo.dentalclinicbackend.repository;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import com.kmgeronimo.dentalclinicbackend.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByAdminEntity(AdminEntity adminEntity);
    VerificationToken findByToken(String token);
}
