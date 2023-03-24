package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.error.UserNotFoundException;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Patient;
import com.kmgeronimo.dentalclinicbackend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    @Autowired
    private PatientService service;

    @PostMapping("/registration")
    public ResponseEntity<String> registerPatient(@RequestParam Map<String, String> request){
        Patient patient = Patient.builder()
                .firstname(request.get("firstname"))
                .middlename(request.get("middlename"))
                .lastname(request.get("lastname"))
                .gender(request.get("gender"))
                .address(request.get("address"))
                .email(request.get("email"))
                .contactNumber(request.get("phoneNumber"))
                .birthday(LocalDate.parse(request.get("birthday")))
                .age(Integer.parseInt(request.get("age")))
                .profile(request.get("profile"))
                .username(request.get("username"))
                .password(request.get("password"))
                .verified(true)
                .build();
        String result = service.registerPatient(patient);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<PatientEntity>> fetchAllPatient(){
        List<PatientEntity> patientEntityList = service.fetchAllPatient();
        return ResponseEntity.ok().body(patientEntityList);
    }
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Patient> fetchPatient(@PathVariable("id") String id) throws UserNotFoundException {
        Patient patient = service.fetchPatient(id);
        return ResponseEntity.ok().body(patient);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage> updatePatientInformation(@PathVariable("id") String id,
                                                                    @RequestParam Map<String, String> request){
        Patient patient = Patient.builder()
                .firstname(request.get("firstname"))
                .middlename(request.get("middlename"))
                .lastname(request.get("lastname"))
                .email(request.get("email"))
                .address(request.get("address"))
                .birthday(LocalDate.parse(request.get("birthday")))
                .gender(request.get("gender"))
                .profile(request.get("profile"))
                .verified(Boolean.parseBoolean(request.get("verified")))
                .build();
        ResponseMessage responseMessage = service.updatePatientInformation(id, patient);
        return ResponseEntity.status(responseMessage.getStatus()).body(responseMessage);
    }
    @PostMapping("/disable")
    public ResponseEntity<ResponseMessage> disablePatient(@RequestBody AccountDisable accountDisable){
        ResponseMessage responseMessage = service.disablePatient(accountDisable);
        return ResponseEntity.status(responseMessage.getStatus()).body(responseMessage);
    }

}
