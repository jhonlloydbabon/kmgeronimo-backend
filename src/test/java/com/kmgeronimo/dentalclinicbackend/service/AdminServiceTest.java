package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import com.kmgeronimo.dentalclinicbackend.model.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService service;
    @Test
    public void  testAdminRegistration() throws Exception {
        Admin admin = Admin.builder()
                .adminFirstname("Jhon Lloyd")
                .adminMiddlename("Nabual")
                .adminLastname("Babon")
                .birthday(LocalDate.of(1999,12, 30))
                .email("jlbabon12@gmail.com")
                .username("admin")
                .password("admin123")
                .role("ADMIN")
                .enabled(true)
                .build();
        service.registerAdmin(admin);
    }
}