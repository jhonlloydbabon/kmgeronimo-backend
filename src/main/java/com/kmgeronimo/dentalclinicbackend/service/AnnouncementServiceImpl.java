package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AnnouncementEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.enums.AnouncementType;
import com.kmgeronimo.dentalclinicbackend.model.Announcement;
import com.kmgeronimo.dentalclinicbackend.repository.AnnouncementRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{

    @Autowired
    private AnnouncementRepository announcementRepository;
    @Override
    public ResponseMessage createAnnouncement(Announcement announcement) {
        AnnouncementEntity announcementEntity = new AnnouncementEntity();
        BeanUtils.copyProperties(announcement, announcementEntity);
        announcementEntity.setType(AnouncementType.valueOf(announcement.getType()));
        announcementEntity.setIsAvailable(true);
        announcementRepository.save(announcementEntity);
        return new ResponseMessage(HttpStatus.OK, "Created successfully!");
    }

    @Override
    public List<AnnouncementEntity> getAllAnnouncement() { return announcementRepository.findAll(); }
}
