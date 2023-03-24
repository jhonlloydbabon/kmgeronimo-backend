package com.kmgeronimo.dentalclinicbackend.repository;

import com.kmgeronimo.dentalclinicbackend.entity.PasswordVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRecoveryTokenRepository extends JpaRepository<PasswordVerificationToken,
        Long> {
    PasswordVerificationToken findByToken(String id);
}
