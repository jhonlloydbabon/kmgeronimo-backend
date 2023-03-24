package com.kmgeronimo.dentalclinicbackend.model;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dentist {
    private String fullname;
    private LocalDate birthday;
    private String address;
    private String gender;
    private String contactNumber;
    private String email;
    private String specialty;
    private String username;
    private String password;
    private String profile;
}
