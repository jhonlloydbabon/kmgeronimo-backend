package com.kmgeronimo.dentalclinicbackend.model;

import com.kmgeronimo.dentalclinicbackend.entity.DentistEntity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {
    private LocalDate date;
    private LocalTime timeStart;
    private String dentistId;
}
