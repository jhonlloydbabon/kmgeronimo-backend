package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.model.SMSSendRequest;
import com.kmgeronimo.dentalclinicbackend.service.SMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sms")
@Slf4j
public class SMSController {

    @Autowired
    private SMSService smsService;

    @PostMapping("/processSMS")
    public  String processSMS(@RequestBody SMSSendRequest smsSendRequest){
        return smsService.sendSMS(smsSendRequest.getDestinationSMSNumber(), smsSendRequest.getSmsMessage());
    }
}
