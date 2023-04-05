package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.enums.AppointmentStatus;
import com.kmgeronimo.dentalclinicbackend.enums.PaymentStatus;
import com.kmgeronimo.dentalclinicbackend.entity.*;
import com.kmgeronimo.dentalclinicbackend.model.Appointment;
import com.kmgeronimo.dentalclinicbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private DentalServiceRepository dentalServiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InstallmentRepository installmentRepository;

    @Override
    public ResponseMessage createAppointment(Appointment appointment) {
            PatientEntity patient = patientRepository.findById(appointment.getPatient()).get();
            Optional<AppointmentsEntity> optionalAppointments = Optional.ofNullable(appointmentRepository.findByPatient(patient));
            if(optionalAppointments.isPresent()){
                return new ResponseMessage(HttpStatus.CONFLICT, "Appointment for "+patient.getFirstname()+" already exist");
            }
            AppointmentsEntity appointmentsEntity = new AppointmentsEntity();
            DentistEntity dentist = dentistRepository.findById(appointment.getDentist()).get();
            List<DentalServiceEntity> dentalServiceEntities = new ArrayList<>();
            for(String value: appointment.getDentalServices()){
                DentalServiceEntity entity = dentalServiceRepository.findById(value).get();
                dentalServiceEntities.add(entity);
            }
            appointmentsEntity.setPatient(patient);
            appointmentsEntity.setDentist(dentist);
            appointmentsEntity.setDentalServices(dentalServiceEntities);
            appointmentsEntity.setDateSubmitted(LocalDate.now());
            appointmentsEntity.setAppointmentDate((LocalDate) appointment.getDate());
            appointmentsEntity.setTimeStart((LocalTime) appointment.getTimeStart());
            appointmentsEntity.setTimeEnd((LocalTime) appointment.getTimeEnd());
            appointmentsEntity.setStatus(AppointmentStatus.PENDING);
            appointmentsEntity.setDoneReadingTC((Boolean) true);
            appointmentRepository.save(appointmentsEntity);

            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setMethod(appointment.getMethod());
            paymentEntity.setType(appointment.getType());
            paymentEntity.setTotalPayment((Double) appointment.getTotalAmount());
            paymentEntity.setPatient(patient);
            paymentEntity.setStatus(PaymentStatus.PENDING);
            paymentEntity.setPaymentPhoto(null);
            paymentRepository.save(paymentEntity);
            return new ResponseMessage(HttpStatus.OK, "Created appointment successfully!");
    }

    @Override
    public List<AppointmentsEntity> fetchAllAppointmentEntity() {
        return appointmentRepository.findAll();
    }
}
