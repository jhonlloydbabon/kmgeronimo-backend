package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.model.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {
    @Autowired
    private AdminServiceImpl service;

    @Test
    public void registerTest() throws Exception {
        Admin admin = Admin.builder()
                .firstname("Jhon Lloyd")
                .middlename("Nabual")
                .lastname("Babon")
                .email("jlbabon12@gmail.com")
                .address("Malabon")
                .contactNumber("09222463571")
                .gender("Male")
                .birthday(LocalDate.parse("1999-12-30"))
                .username("admin")
                .password("123")
                .role("Admin")
                .build();

        service.registerAdmin(admin);
    }
}