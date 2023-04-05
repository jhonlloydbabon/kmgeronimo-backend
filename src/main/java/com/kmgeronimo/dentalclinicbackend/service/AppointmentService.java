package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AppointmentsEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.Appointment;

import java.util.List;

public interface AppointmentService {
    ResponseMessage createAppointment(Appointment appointment);

    List<AppointmentsEntity> fetchAllAppointmentEntity();
}
