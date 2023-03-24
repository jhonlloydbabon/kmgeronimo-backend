package com.kmgeronimo.dentalclinicbackend.repository;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, String> {
    AdminEntity findByUsername(String username);
    AdminEntity findByEmail(String email);
}
