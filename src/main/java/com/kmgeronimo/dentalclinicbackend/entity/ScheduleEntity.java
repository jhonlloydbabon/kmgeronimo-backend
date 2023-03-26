package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedule")
public class ScheduleEntity {
    @GenericGenerator(
            name = "uuid",
            strategy = "uuid2"
    )
    @GeneratedValue( generator = "uuid")
    @Id
    private String scheduleId;
    private LocalDate date;
    private LocalTime timeStart;
    @OneToOne
    @JoinColumn(
            name = "dentistId",
            referencedColumnName = "dentistId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_DENTIST_SCHEDULE")
    )
    private DentistEntity dentistEntity;
}
