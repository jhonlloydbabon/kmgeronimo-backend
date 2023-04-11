package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.enums.AppointmentStatus;
import com.kmgeronimo.dentalclinicbackend.enums.PaymentStatus;
import com.kmgeronimo.dentalclinicbackend.entity.*;
import com.kmgeronimo.dentalclinicbackend.model.Appointment;
import com.kmgeronimo.dentalclinicbackend.model.AppointmentStatusModel;
import com.kmgeronimo.dentalclinicbackend.model.History;
import com.kmgeronimo.dentalclinicbackend.repository.*;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public ResponseMessage createAppointment(Appointment appointment) {

            PatientEntity patient = patientRepository.findById(appointment.getPatient()).get();
            Optional<AppointmentsEntity> optionalAppointments = Optional.ofNullable(appointmentRepository.findByPatient(patient));
            if(optionalAppointments.isPresent() && !optionalAppointments.get().getStatus().equals(AppointmentStatus.CANCELLED)){
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
            InsuranceEntity insurance = new InsuranceEntity();
            if(appointment.getMethod().equalsIgnoreCase("hmo")){
                insurance = insuranceRepository.findById(appointment.getInsuranceId()).get();
            }
            paymentEntity.setMethod(appointment.getMethod());
            paymentEntity.setInsurance(insurance);
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

    @Override
    public ResponseMessage editAppointmentStatus(String id, AppointmentStatusModel statusModel) {
        AppointmentsEntity appointmentsEntity = appointmentRepository.findById(id).get();
        appointmentsEntity.setStatus(AppointmentStatus.valueOf(statusModel.getStatus()));
        History history = null;
        if(appointmentsEntity.getStatus().equals(AppointmentStatus.DONE)){
             history = History.builder()
                    .name(appointmentsEntity.getPatient().getFirstname()+" "+appointmentsEntity.getPatient().getLastname())
                    .appointmentDate(LocalDate.now())
                    .description("Successful appointment")
                    .status(AppointmentStatus.DONE.toString())
                    .build();
            HistoryEntity historyEntity = new HistoryEntity();
            BeanUtils.copyProperties(history, historyEntity);
            historyRepository.save(historyEntity);
        }
        if(appointmentsEntity.getStatus().equals(AppointmentStatus.CANCELLED)){
            history = History.builder()
                    .name(appointmentsEntity.getPatient().getFirstname()+" "+appointmentsEntity.getPatient().getLastname())
                    .appointmentDate(LocalDate.now())
                    .description(statusModel.getDescription())
                    .status(AppointmentStatus.CANCELLED.toString())
                    .build();
            HistoryEntity historyEntity = new HistoryEntity();
            BeanUtils.copyProperties(history, historyEntity);
            historyRepository.save(historyEntity);

            PatientEntity patient = patientRepository.findById(appointmentsEntity.getPatient().getPatientId()).get();
            PaymentEntity paymentEntity = paymentRepository.findByPatient(patient);
            paymentRepository.delete(paymentEntity);
        }
        appointmentRepository.save(appointmentsEntity);
        return new ResponseMessage(HttpStatus.OK, "The appointment has been "+statusModel.getStatus());
    }

    @Override
    public ResponseMessage deleteAppointment(String id) {
        AppointmentsEntity appointmentsEntity = appointmentRepository.findById(id).get();
        if(appointmentsEntity.getStatus().equals(AppointmentStatus.CANCELLED)){
            return new ResponseMessage(HttpStatus.NOT_ACCEPTABLE, "You can't delete this appointment");
        }
        PatientEntity patientEntity = patientRepository.findById(appointmentsEntity.getPatient().getPatientId()).get();
        PaymentEntity paymentEntity = paymentRepository.findByPatient(patientEntity);
        paymentRepository.delete(paymentEntity);
        appointmentRepository.delete(appointmentsEntity);
        return new ResponseMessage(HttpStatus.OK, "Delete successfully");
    }
}
