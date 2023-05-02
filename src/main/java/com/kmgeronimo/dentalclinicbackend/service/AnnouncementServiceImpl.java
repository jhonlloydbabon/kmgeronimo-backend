package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AnnouncementEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.enums.AnouncementType;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
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

    @Override
    public ResponseMessage updateAnnouncement(String id, Announcement announcement) {
        AnnouncementEntity announcementEntity = announcementRepository.findById(id).get();
        announcementEntity.setTitle(announcement.getTitle());
        announcementEntity.setDescription(announcement.getDescription());
        announcementEntity.setType(AnouncementType.valueOf(announcement.getType()));
        announcementEntity.setPicture(announcement.getPicture());
        announcementRepository.save(announcementEntity);
        return new ResponseMessage(HttpStatus.OK, "Update announcement successfully!");
    }

    @Override
    public ResponseMessage deleteAnnouncement(String id) {
        AnnouncementEntity announcementEntity = announcementRepository.findById(id).get();
        announcementRepository.delete(announcementEntity);
        return new ResponseMessage(HttpStatus.OK, "Delete announcement successfully!");
    }

    @Override
    public void disableAnnouncement(AccountDisable accountDisable) {
        AnnouncementEntity announcementEntity = announcementRepository.findById(accountDisable.getId()).get();
        announcementEntity.setIsAvailable(accountDisable.getVerified());
        announcementRepository.save(announcementEntity);
    }
}
