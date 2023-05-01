package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.AnnouncementEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.Announcement;
import com.kmgeronimo.dentalclinicbackend.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    @PostMapping("/")
    public ResponseEntity<ResponseMessage> createAnnouncement(@RequestBody Announcement announcement){
        ResponseMessage message = service.createAnnouncement(announcement);
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @GetMapping("/")
    public List<AnnouncementEntity> getAllAnnouncement(){ return service.getAllAnnouncement(); }
}
