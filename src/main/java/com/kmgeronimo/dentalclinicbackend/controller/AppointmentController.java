package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.AppointmentsEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.Appointment;
import com.kmgeronimo.dentalclinicbackend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping("/")
    public ResponseEntity<ResponseMessage> createAppointment(@RequestBody Appointment appointment){
        ResponseMessage message = service.createAppointment(appointment);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
    @GetMapping("/")

    public List<AppointmentsEntity> fetchAllAppointmentEntity(){
        return service.fetchAllAppointmentEntity();
    }
}
