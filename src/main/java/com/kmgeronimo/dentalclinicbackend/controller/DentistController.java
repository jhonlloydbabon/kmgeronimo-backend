package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.DentistEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Dentist;
import com.kmgeronimo.dentalclinicbackend.service.DentistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin( value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/dentist")
public class DentistController {
    @Autowired
    private DentistService dentistService;

    @PostMapping("/register")
    private ResponseEntity<ResponseMessage> saveDentistInformation(@RequestBody Dentist dentist){
        ResponseMessage message = dentistService.saveDentistInformation(dentist);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
    @GetMapping("/")
    public List<DentistEntity> findAllDentist(){
        return dentistService.findAllDentist();
    }
    @PutMapping("/updatedentist/{id}")
    public ResponseEntity<ResponseMessage> updateDentistInformation(@PathVariable("id") String id,
                                                                    @RequestBody Dentist dentist){
        ResponseMessage message = dentistService.updateDentistInformation(id, dentist);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
    @PostMapping("/disable")
    public ResponseEntity<ResponseMessage> disableDentist(@RequestBody AccountDisable accountDisable){
        ResponseMessage message = dentistService.disableDentist(accountDisable);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
}
