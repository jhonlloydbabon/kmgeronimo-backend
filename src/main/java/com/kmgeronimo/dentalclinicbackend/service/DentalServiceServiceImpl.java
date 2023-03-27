package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.DentalServiceEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.DentalService;
import com.kmgeronimo.dentalclinicbackend.repository.DentalServiceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class DentalServiceServiceImpl implements DentalServiceService{

    @Autowired
    private DentalServiceRepository dentalServiceRepository;

    @Override
    public ResponseMessage addServices(DentalService dentalService) {
        DentalServiceEntity checkService = dentalServiceRepository.findByName(dentalService.getName());
        if(!Objects.isNull(checkService)){
            return new ResponseMessage(HttpStatus.CONFLICT, "Service already exist!");
        }
        DentalServiceEntity serviceEntity = new DentalServiceEntity();
        BeanUtils.copyProperties(dentalService, serviceEntity);
        serviceEntity.setPrice((Double) dentalService.getPrice());
        serviceEntity.setDuration((LocalTime) dentalService.getDuration());
        serviceEntity.setIsAvailable(true);
        dentalServiceRepository.save(serviceEntity);
        return new ResponseMessage(HttpStatus.OK, "Created new service.");
    }

    @Override
    public List<DentalServiceEntity> fecthAllDentalServices() {
        return dentalServiceRepository.findAll();
    }
}
