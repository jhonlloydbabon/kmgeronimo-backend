package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.Schedule;

public interface ScheduleService {
    ResponseMessage saveSchedule(Schedule schedule);
}
