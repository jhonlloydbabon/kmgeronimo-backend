package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AnnouncementEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    ResponseMessage createAnnouncement(Announcement announcement);

    List<AnnouncementEntity> getAllAnnouncement();
}
