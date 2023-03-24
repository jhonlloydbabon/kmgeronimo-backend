package com.kmgeronimo.dentalclinicbackend.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {
    private String firstname;
    private String middlename;
    private String lastname;
    private LocalDate birthday;
    private String email;
    private String address;
    private String username;
    private String password;
    private String profile;
    private String gender;
    private String contactNumber;
    private String role;
    private boolean enabled;
}
