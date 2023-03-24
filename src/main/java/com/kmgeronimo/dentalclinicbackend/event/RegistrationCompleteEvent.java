package com.kmgeronimo.dentalclinicbackend.event;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Clock;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private AdminEntity admin;
    private String applicationUrl;

    public RegistrationCompleteEvent(AdminEntity admin, String applicationUrl) {
        super(admin);
        this.admin = admin;
        this.applicationUrl = applicationUrl;
    }
}
