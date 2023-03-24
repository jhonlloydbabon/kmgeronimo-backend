package com.kmgeronimo.dentalclinicbackend.event.listener;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import com.kmgeronimo.dentalclinicbackend.event.RegistrationCompleteEvent;
import com.kmgeronimo.dentalclinicbackend.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private AdminService service;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        AdminEntity adminEntity = event.getAdmin();
        String token = UUID.randomUUID().toString();
        service.saveVerificationTokenForAdmin(adminEntity, token);

        String url = event.getApplicationUrl()+"/verifyRegistrationToken?token="+token;
        log.info("Click the link to verify account: "+url);
    }
}
