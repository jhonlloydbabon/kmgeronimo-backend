package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.model.ChatMessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@CrossOrigin(value = "http://localhost:3000/")
@RestController
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message") // /app/message default url
    @SendTo("/chatroom/public")
    public ChatMessageModel receivePublicMessage(@Payload ChatMessageModel message){
        return message;
    }

    @MessageMapping("/private-message")
    public ChatMessageModel receivePrivateMessage(@Payload ChatMessageModel message){
        simpMessagingTemplate.convertAndSendToUser(message.getRecipientName(), "/private", message); //this should be look like /user/jl/private
        return message;
    }

}
