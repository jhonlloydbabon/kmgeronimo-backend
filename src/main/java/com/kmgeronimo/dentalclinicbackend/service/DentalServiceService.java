package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.DentalServiceEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.DentalService;

import java.util.List;

public interface DentalServiceService {
    ResponseMessage addServices(DentalService dentalService);

    List<DentalServiceEntity> fecthAllDentalServices();

    ResponseMessage updateDentalService(String id, DentalService dentalService);

    ResponseMessage disableDentalService(AccountDisable disable);

    ResponseMessage deleteDentalService(String id);
}
