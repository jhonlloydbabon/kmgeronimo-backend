package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "Service")
public class DentalServiceEntity {
    @Id
    @GenericGenerator( name = "uuid", strategy = "uuid2")
    @GeneratedValue( generator = "uuid")
    private String serviceId;
    private String name;
    private String type;
    private String description;
    private LocalTime duration;
    private Double price;
    private Boolean isAvailable;
}
