package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Data
@Table( name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String patientId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String gender;
    private String address;
    private String email;
    private String contactNumber;
    private Integer age;
    private LocalDate birthday;
    @Lob
    private String profile;
    private String username;
    @Column(length = 60)
    private String password;
    private Boolean verified;
}
