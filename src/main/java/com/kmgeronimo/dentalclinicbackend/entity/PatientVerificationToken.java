package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PatientVerificationToken {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String Id;
    private String token;
    private Date expirationTime;

    private static final int EXPIRATION_TIME = 1440;

    @OneToOne
    @JoinColumn(
            name = "patientId",
            referencedColumnName = "patientId",
            foreignKey = @ForeignKey(name = "FK_PATIENT_VERIFICATION_TOKEN")
    )
    private PatientEntity patientEntity;

    public PatientVerificationToken(String token){
        super();
        this.token = token;
        this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
    }
    public PatientVerificationToken(String token, PatientEntity patientEntity){
        super();
        this.token = token;
        this.patientEntity = patientEntity;
        this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
    }

    private Date calculateExpirationTime(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }

}
