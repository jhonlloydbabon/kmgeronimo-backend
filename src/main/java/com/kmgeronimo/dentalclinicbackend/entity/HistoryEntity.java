package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "history")
public class HistoryEntity {
    @Id
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String historyId;
    private String name;
    private LocalDate appointmentDate;
    private String description;
    private String status;
}
