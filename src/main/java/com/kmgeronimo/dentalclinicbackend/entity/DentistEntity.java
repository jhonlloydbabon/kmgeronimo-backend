package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dentist")
public class DentistEntity {
    @Id
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String dentistId;
    private String fullname;
    private LocalDate birthday;
    private Integer age;
    private String address;
    private String gender;
    private String contactNumber;
    private String email;
    private String specialty;
    private String username;
    private String password;
    private Boolean verified;
    @Lob
    private String profile;
}
