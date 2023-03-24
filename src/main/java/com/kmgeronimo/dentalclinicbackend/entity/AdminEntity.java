package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "admin")
public class AdminEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String adminId;
    private String adminFirstname;
    private String adminMiddlename;
    private String adminLastname;
    private LocalDate birthday;
    private String address;
    private String email;
    private String gender;
    private String contactNumber;
    private String username;
    private String password;
    private String role;
    @Lob
    private String profile;
    private boolean enabled;
}
