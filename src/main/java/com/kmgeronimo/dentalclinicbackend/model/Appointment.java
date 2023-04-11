package com.kmgeronimo.dentalclinicbackend.model;

import com.kmgeronimo.dentalclinicbackend.entity.DentalServiceEntity;
import com.kmgeronimo.dentalclinicbackend.entity.DentistEntity;
import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    private String patient;
    private String dentist;
    private String [] dentalServices;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private Double totalAmount;
    private String method;
    private String type;
    private String insuranceId;
}
