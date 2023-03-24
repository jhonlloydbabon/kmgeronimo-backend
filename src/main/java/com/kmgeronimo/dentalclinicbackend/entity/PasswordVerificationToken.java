package com.kmgeronimo.dentalclinicbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PasswordVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne
    @JoinColumn(
            name = "adminId",
            nullable = false,
            referencedColumnName = "adminId",
            foreignKey = @ForeignKey(name = "FK_ADMIN_VERIFY_TOKEN")
    )
    private AdminEntity adminEntity;
    private static final int EXPIRATION_TIME = 10;

    public PasswordVerificationToken(String token){
        super();
        this.token = token;
        this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
    }

    public PasswordVerificationToken(AdminEntity adminEntity, String token){
        super();
        this.adminEntity = adminEntity;
        this.token = token;
        this.expirationTime = calculateExpirationTime(EXPIRATION_TIME);
    }

    private Date calculateExpirationTime(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
