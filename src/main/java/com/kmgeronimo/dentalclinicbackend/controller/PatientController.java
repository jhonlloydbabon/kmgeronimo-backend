package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.error.UserNotFoundException;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.AccountLogin;
import com.kmgeronimo.dentalclinicbackend.model.Patient;
import com.kmgeronimo.dentalclinicbackend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    @Autowired
    private PatientService service;

    @PostMapping("/registration")
    public ResponseEntity<ResponseMessage> registerPatient(@RequestBody Patient patient){
        ResponseMessage result = service.registerPatient(patient);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> loginPatient(@RequestBody AccountLogin accountLogin){
        ResponseMessage message = service.loginPatientAccount(accountLogin);
        return ResponseEntity.status(message.getStatus()).body(message);
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
                                                                    @RequestBody Patient patient){
        ResponseMessage responseMessage = service.updatePatientInformation(id, patient);
        return ResponseEntity.status(responseMessage.getStatus()).body(responseMessage);
    }
    @PostMapping("/disable")
    public ResponseEntity<ResponseMessage> disablePatient(@RequestBody AccountDisable accountDisable){
        ResponseMessage responseMessage = service.disablePatient(accountDisable);
        return ResponseEntity.status(responseMessage.getStatus()).body(responseMessage);
    }


    @PostMapping("/checkEmail/{email}")
    private ResponseEntity<ResponseMessage> isEmailAlreadyExist(@PathVariable("email")String email){
        ResponseMessage message = service.isEmailAlreadyExist(email);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
    @PostMapping("/checkContactNumber/{contactNumber}")
    private ResponseEntity<ResponseMessage> isContactNumberAlreadyExist(@PathVariable("contactNumber")String contactNumber){
        ResponseMessage message = service.isContactNumberAlreadyExist(contactNumber);
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @PostMapping("/ifValidPatient/{token}")
    private ResponseEntity<ResponseMessage> checkIfValidPatient(@PathVariable("token") String token){
        ResponseMessage message = service.checkIfValidPatient(token);
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @GetMapping("/fetchPatient/{token}")
    private PatientEntity fetchPatientEntityByToken(@PathVariable("token") String token) throws Exception {
        return service.fetchPatientEntityByToken(token);
    }
}
