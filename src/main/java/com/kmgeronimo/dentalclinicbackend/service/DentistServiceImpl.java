package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import com.kmgeronimo.dentalclinicbackend.entity.DentistEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Dentist;
import com.kmgeronimo.dentalclinicbackend.repository.DentistRepository;
import org.aspectj.weaver.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class DentistServiceImpl implements DentistService{
    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseMessage saveDentistInformation(Dentist dentist) {
        DentistEntity dentistEntity = new DentistEntity();
        BeanUtils.copyProperties(dentist, dentistEntity);
        dentistEntity.setAge(calculateDentistAge(dentistEntity.getBirthday()));
        dentistEntity.setPassword(passwordEncoder.encode(dentistEntity.getPassword()));
        dentistEntity.setVerified(true);
        dentistRepository.save(dentistEntity);
        return new ResponseMessage(HttpStatus.OK, "Registered Successfully!");
    }

    @Override
    public List<DentistEntity> findAllDentist() {
        return dentistRepository.findAll();
    }

    @Override
    public ResponseMessage updateDentistInformation(String id, Dentist dentist) {
        DentistEntity dentistEntity = dentistRepository.findById(id).get();
        dentistEntity.setFullname(dentist.getFullname());
        dentistEntity.setBirthday(dentist.getBirthday());
        dentistEntity.setAddress(dentist.getAddress());
        dentistEntity.setContactNumber(dentist.getContactNumber());
        dentistEntity.setContactNumber(dentist.getContactNumber());
        dentistEntity.setEmail(dentist.getEmail());
        dentistEntity.setSpecialty(dentist.getSpecialty());
        dentistEntity.setProfile(dentist.getProfile());
        dentistRepository.save(dentistEntity);
        return new ResponseMessage(HttpStatus.OK, "Update successfully.");
    }

    @Override
    public ResponseMessage disableDentist(AccountDisable accountDisable) {
        DentistEntity dentistEntity = dentistRepository.findById(accountDisable.getId()).get();
        dentistEntity.setVerified(accountDisable.getVerified());
        dentistRepository.save(dentistEntity);
        return new ResponseMessage(HttpStatus.OK, "Disable Account Successfully.");
    }

    public Integer calculateDentistAge(LocalDate birthday){
        LocalDate current = LocalDate.now();
        return Period.between(birthday, current).getYears();
    }
}
