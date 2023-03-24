package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.DentistEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Dentist;

import java.util.List;

public interface DentistService {
    ResponseMessage saveDentistInformation(Dentist dentist);

    List<DentistEntity> findAllDentist();

    ResponseMessage updateDentistInformation(String id, Dentist dentist);

    ResponseMessage disableDentist(AccountDisable accountDisable);
}
