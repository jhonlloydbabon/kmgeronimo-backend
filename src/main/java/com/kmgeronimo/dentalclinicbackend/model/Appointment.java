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
    private PatientEntity patientId;
    private DentistEntity dentistId;
    private List<DentalServiceEntity> dentalServiceEntities;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String status;
    private Double totalAmount;
}
