package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.DentistEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.entity.ScheduleEntity;
import com.kmgeronimo.dentalclinicbackend.model.Schedule;
import com.kmgeronimo.dentalclinicbackend.repository.DentistRepository;
import com.kmgeronimo.dentalclinicbackend.repository.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private DentistRepository dentistRepository;

    @Override
    public ResponseMessage saveSchedule(Schedule schedule) {
        ScheduleEntity scheduleCheck= scheduleRepository.findByTimeStart(schedule.getTimeStart());
        if(!Objects.isNull(scheduleCheck)){
            return new ResponseMessage(HttpStatus.CONFLICT, "Time start and time end already exist!");
        }
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        DentistEntity dentist = dentistRepository.findById(schedule.getDentistId()).get();
        scheduleEntity.setDate(schedule.getDate());
        scheduleEntity.setTimeStart((LocalTime) schedule.getTimeStart());
        scheduleEntity.setDentistEntity(dentist);
        scheduleRepository.save(scheduleEntity);
        return new ResponseMessage(HttpStatus.OK, "Created successfully!");
    }
}
