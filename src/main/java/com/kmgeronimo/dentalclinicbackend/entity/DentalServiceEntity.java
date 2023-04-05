package com.kmgeronimo.dentalclinicbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "services")
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

    @JsonIgnore // to ignore this
    @ManyToMany(mappedBy = "dentalServices") // name of the table in the Appointment;
    List<AppointmentsEntity> appointments = new ArrayList<>();
}
