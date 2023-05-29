package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.error.UserNotFoundException;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.AccountLogin;
import com.kmgeronimo.dentalclinicbackend.model.Patient;

import java.util.List;
import java.util.Map;

public interface PatientService {

    ResponseMessage registerPatient(Patient patient);

    List<PatientEntity> fetchAllPatient();

    Patient fetchPatient(String id) throws UserNotFoundException;

    ResponseMessage updatePatientInformation(String id, Patient patient);

    ResponseMessage disablePatient(AccountDisable accountDisable);

    ResponseMessage loginPatientAccount(AccountLogin accountLogin);

    ResponseMessage isEmailAlreadyExist(String email);

    ResponseMessage isContactNumberAlreadyExist(String contactNumber);

    ResponseMessage checkIfValidPatient(String token);

    PatientEntity fetchPatientEntityByToken(String token);
}
