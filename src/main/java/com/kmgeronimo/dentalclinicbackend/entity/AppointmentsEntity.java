package com.kmgeronimo.dentalclinicbackend.entity;

import com.kmgeronimo.dentalclinicbackend.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "appointment")
public class AppointmentsEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String appointmentId;

    @ManyToOne
    @JoinColumn(name = "patientId", foreignKey = @ForeignKey(name = "FK_PATIENT_APPOINTMENT"))
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "dentistId", foreignKey = @ForeignKey(name = "FK_DENTIST_APPOINTMENT"))
    private DentistEntity dentist;

    @ManyToMany
    @JoinTable(
            name = "appointment_services",
            joinColumns = @JoinColumn(name = "appointmentId"),
            inverseJoinColumns = @JoinColumn(name = "service_id"),
            foreignKey = @ForeignKey(name = "FK_APPOINTMENT_SERVICES"),
            inverseForeignKey = @ForeignKey(name = "FK_SERVICE_APPOINTMENT"
            ))
    private List<DentalServiceEntity> dentalServices = new ArrayList<>();

    private LocalDate dateSubmitted;
    private LocalDate appointmentDate;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private AppointmentStatus status;
    private Boolean doneReadingTC;
}