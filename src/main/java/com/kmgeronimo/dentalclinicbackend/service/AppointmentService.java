package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AppointmentsEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.Appointment;
import com.kmgeronimo.dentalclinicbackend.model.AppointmentStatusModel;

import java.util.List;

public interface AppointmentService {
    ResponseMessage createAppointment(Appointment appointment);

    List<AppointmentsEntity> fetchAllAppointmentEntity();

    ResponseMessage editAppointmentStatus(String id, AppointmentStatusModel appointmentStatusModel);

    ResponseMessage deleteAppointment(String id);

    AppointmentsEntity fetchAppointmentEntity(String id);

    List<AppointmentsEntity> fetchPatientAppointmentList(String id);
}
