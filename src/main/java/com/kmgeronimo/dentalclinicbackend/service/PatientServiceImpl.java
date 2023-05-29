package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.*;
import com.kmgeronimo.dentalclinicbackend.error.UserNotFoundException;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.AccountLogin;
import com.kmgeronimo.dentalclinicbackend.model.Patient;
import com.kmgeronimo.dentalclinicbackend.repository.PatientRepository;
import com.kmgeronimo.dentalclinicbackend.repository.PatientVerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService{
    @Autowired
    private PatientRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PatientVerificationTokenRepository patientVerificationTokenRepository;

    @Override
    public ResponseMessage registerPatient(Patient patient) {
        PatientEntity searchPatient = repository.findByContactNumberAndEmail(patient.getContactNumber(), patient.getEmail());
        if(!Objects.isNull(searchPatient)){
            return new ResponseMessage(HttpStatus.OK, "Email or phone number already exist");
        }

        PatientEntity patientEntity = new PatientEntity();
        BeanUtils.copyProperties(patient, patientEntity);

        if(patient.getHaveInsurance().equalsIgnoreCase("no")){
            patientEntity.setInsurance(null);
        }
        if(patient.getHaveInsurance().equalsIgnoreCase("yes")){
            List<InsuranceEntity> insuranceEntities = patient.getInsuranceInfo().stream()
                            .map((val)->{
                                InsuranceEntity entity = new InsuranceEntity();
                                entity.setCompany(val.getCompany());
                                entity.setCardNumber(val.getCardNumber());
                                entity.setCard(val.getCard());
                                return entity;
                            }).collect(Collectors.toList());
            patientEntity.setInsurance(insuranceEntities);
        }

        patientEntity.setPassword(passwordEncoder.encode(patient.getPassword()));
        patientEntity.setAge(calculateAge(patient.getBirthday()));
        patientEntity.setVerified(true);
        repository.save(patientEntity);
        return new ResponseMessage(HttpStatus.OK, "Account Registered Successfully!");
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
        patientEntity.setFirstname(patient.getFirstname());
        patientEntity.setMiddlename(patient.getMiddlename());
        patientEntity.setLastname(patient.getLastname());
        patientEntity.setBirthday(patient.getBirthday());
        patientEntity.setAge(calculateAge(patient.getBirthday()));
        patientEntity.setAddress(patient.getAddress());
        patientEntity.setGender(patient.getGender());
        patientEntity.setContactNumber(patient.getContactNumber());
        patientEntity.setEmail(patient.getEmail());
        patientEntity.setProfile(patient.getProfile());
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

    @Override
    public ResponseMessage loginPatientAccount(AccountLogin accountLogin) {
        Optional<PatientEntity> patientEntity = Optional.ofNullable(repository.findByUsername(accountLogin.getUsername()));
        if(!patientEntity.isPresent()){
            return new ResponseMessage(HttpStatus.NOT_FOUND, "Account doesn't exist");
        }
        if(!patientEntity.get().getUsername().equals(accountLogin.getUsername()) || !passwordEncoder.matches(accountLogin.getPassword(), patientEntity.get().getPassword())){
            return new ResponseMessage(HttpStatus.NOT_ACCEPTABLE, "Invalid username or password");
        }
        if(patientEntity.get().getVerified()==false) return new ResponseMessage(HttpStatus.NOT_ACCEPTABLE, "Kindly contact the admin to reactivation of your account");
        Optional<PatientVerificationToken> patientVerificationToken = Optional.ofNullable(patientVerificationTokenRepository.findByPatientEntity(patientEntity.get()));
        if(patientVerificationToken.isPresent()){
            patientVerificationTokenRepository.delete(patientVerificationToken.get());
        }

        String token = UUID.randomUUID().toString();
        patientVerificationToken = Optional.of(new PatientVerificationToken(token, patientEntity.get()));
        patientVerificationTokenRepository.save(patientVerificationToken.get());
        return new ResponseMessage(HttpStatus.OK, token);
    }

    @Override
    public ResponseMessage isEmailAlreadyExist(String email) {
        PatientEntity patientEntity = repository.findByEmail(email);
        if(!Objects.isNull(patientEntity)) return new ResponseMessage(HttpStatus.NOT_ACCEPTABLE, "Email already exist");
        return new ResponseMessage(HttpStatus.OK, "Approved");
    }

    @Override
    public ResponseMessage isContactNumberAlreadyExist(String contactNumber) {
        PatientEntity patientEntity = repository.findByContactNumber(contactNumber);
        if(!Objects.isNull(patientEntity)) return new ResponseMessage(HttpStatus.NOT_ACCEPTABLE, "Contact number already exist");
        return new ResponseMessage(HttpStatus.OK, "Approved");
    }

    @Override
    public ResponseMessage checkIfValidPatient(String token) {
        PatientVerificationToken patientVerificationToken = patientVerificationTokenRepository.findByToken(token);
        if(Objects.isNull(patientVerificationToken)) return new ResponseMessage(HttpStatus.NOT_ACCEPTABLE, "invalid");
        PatientEntity patient = patientVerificationToken.getPatientEntity();
        Calendar calendar = Calendar.getInstance();
        if((patientVerificationToken.getExpirationTime().getTime()-calendar.getTime().getTime())<=0){
            generateNewToken(patientVerificationToken.getToken());
            return new ResponseMessage(HttpStatus.OK, "valid");
        }
        return new ResponseMessage(HttpStatus.OK, "valid");
    }

    @Override
    public PatientEntity fetchPatientEntityByToken(String token) { return patientVerificationTokenRepository.findByToken(token).getPatientEntity(); }

    public static Integer calculateAge(LocalDate birthday){
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthday, currentDate).getYears();
    }

    public PatientVerificationToken generateNewToken(String token){
        PatientVerificationToken patientVerificationToken = patientVerificationTokenRepository.findByToken(token);
        patientVerificationToken.setToken(UUID.randomUUID().toString());
        patientVerificationTokenRepository.save(patientVerificationToken);
        return  patientVerificationToken;
    }

}
