package com.kmgeronimo.dentalclinicbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
}