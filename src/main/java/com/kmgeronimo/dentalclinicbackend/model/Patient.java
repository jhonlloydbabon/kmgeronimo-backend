package com.kmgeronimo.dentalclinicbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
    private String firstname;
    private String middlename;
    private String lastname;
    private String gender;
    private String address;
    private String email;
    private String contactNumber;
    private Integer age;
    private LocalDate birthday;
    private String profile;
    private String username;
    private String password;
    private Boolean verified;
}
