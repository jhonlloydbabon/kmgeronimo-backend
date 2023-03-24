package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.PatientEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.error.UserNotFoundException;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Patient;

import java.util.List;

public interface PatientService {

    String registerPatient(Patient patient);

    List<PatientEntity> fetchAllPatient();

    Patient fetchPatient(String id) throws UserNotFoundException;

    ResponseMessage updatePatientInformation(String id, Patient patient);

    ResponseMessage disablePatient(AccountDisable accountDisable);

}
