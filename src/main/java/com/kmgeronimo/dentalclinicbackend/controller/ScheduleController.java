package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.Schedule;
import com.kmgeronimo.dentalclinicbackend.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin( value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @PostMapping ("/")
    public ResponseEntity<ResponseMessage> saveSchedule(@RequestBody Schedule schedule){
        ResponseMessage message = service.saveSchedule(schedule);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
}
