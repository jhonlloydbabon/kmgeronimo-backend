package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "appointment")
public class AppointmentsEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String appointmentId;
    @OneToOne
    @JoinColumn(
            name = "patientId",
            referencedColumnName = "patientId",
            foreignKey = @ForeignKey(name = "FK_PATIENT_APPOINTMENT")
    )
    private PatientEntity patientId;
    @OneToOne
    @JoinColumn(
            name = "dentistId",
            referencedColumnName = "dentistId",
            foreignKey = @ForeignKey(name = "FK_DENTIST_APPOINTMENT")
    )
    private DentistEntity dentistId;
    @OneToMany
    @JoinColumn(
            name = "dentalServiceId",
            referencedColumnName = "dentalServiceId",
            foreignKey = @ForeignKey(name = "FK_APPOINTMENT_SERVICES")
    )
    private List<DentalServiceEntity> dentalServiceEntities;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String status;
    private Double totalAmount;
}
