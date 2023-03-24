package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.error.UserNotFoundException;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Patient;
import com.kmgeronimo.dentalclinicbackend.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService{
    @Autowired
    private PatientRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String registerPatient(Patient patient) {
        PatientEntity searchPatient = repository.findByContactNumberAndEmail(patient.getContactNumber(), patient.getEmail());
        if(!Objects.isNull(searchPatient)){
            return "Email or phone number already exist";
        }
        PatientEntity patientEntity = new PatientEntity();
        BeanUtils.copyProperties(patient, patientEntity);
        patientEntity.setPassword(passwordEncoder.encode(patient.getPassword()));
        patientEntity.setAge(calculateAge(patient.getBirthday()));
        patientEntity.setVerified(true);
        repository.save(patientEntity);
        return "Account Registered Successfully!";
    }

    @Override
    public List<PatientEntity> fetchAllPatient() {
        return repository.findAll();
    }

    @Override
    public Patient fetchPatient(String id) throws UserNotFoundException {
            Optional<PatientEntity> patientEntity = repository.findById(id);
            if(!patientEntity.isPresent()){
                throw new UserNotFoundException("Page Not Found!");
            }
            Patient patient = Patient.builder()
                    .firstname(patientEntity.get().getFirstname())
                    .middlename(patientEntity.get().getMiddlename())
                    .lastname(patientEntity.get().getLastname())
                    .gender(patientEntity.get().getGender())
                    .birthday(patientEntity.get().getBirthday())
                    .address(patientEntity.get().getAddress())
                    .contactNumber(patientEntity.get().getContactNumber())
                    .email(patientEntity.get().getEmail())
                    .profile(patientEntity.get().getProfile())
                    .verified(patientEntity.get().getVerified())
                    .build();
            return patient;
    }

    @Override
    public ResponseMessage updatePatientInformation(String id, Patient patient) {
        PatientEntity patientEntity = repository.findById(id).get();
        if(!patient.getFirstname().equals(patientEntity.getFirstname())){
            patientEntity.setFirstname(patient.getFirstname());
        }
        if(!patient.getMiddlename().equals(patientEntity.getMiddlename())){
            patientEntity.setMiddlename(patient.getMiddlename());
        }
        if(!patient.getLastname().equals(patientEntity.getLastname())){
            patientEntity.setLastname(patient.getLastname());
        }
        if(!patient.getEmail().equals(patientEntity.getEmail())){
            patientEntity.setEmail(patient.getEmail());
        }
        if(!patient.getAddress().equals(patientEntity.getAddress())){
            patientEntity.setAddress(patient.getAddress());
        }
        if(!patient.getBirthday().equals(patientEntity.getBirthday())){
            patientEntity.setBirthday(patient.getBirthday());
            patientEntity.setAge(calculateAge(patient.getBirthday()));
        }
        if(!patient.getGender().equals(patientEntity.getGender())){
            patientEntity.setGender(patient.getGender());
        }
        if(!patient.getProfile().equals(patientEntity.getProfile())){
            patientEntity.setProfile(patient.getProfile());
        }
        if(!patient.getVerified().equals(patientEntity.getVerified())){
            patientEntity.setVerified(patient.getVerified());
        }
        repository.save(patientEntity);
        return new ResponseMessage(HttpStatus.OK, "Update Patient Information Successfully!");
    }

    @Override
    public ResponseMessage disablePatient(AccountDisable accountDisable) {
        PatientEntity patientEntity = repository.findById(accountDisable.getId()).get();
        patientEntity.setVerified(accountDisable.getVerified());
        repository.save(patientEntity);
        return new ResponseMessage(HttpStatus.OK, "Disable" +(accountDisable.getVerified() ? "d" : "")+
                " Account Successfully!");
    }

    public static Integer calculateAge(LocalDate birthday){
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthday, currentDate).getYears();
    }
}