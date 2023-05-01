package com.kmgeronimo.dentalclinicbackend.entity;

import com.kmgeronimo.dentalclinicbackend.enums.AnouncementType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "announcement")
public class AnnouncementEntity {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String announcementId;
    private String title;
    private String description;
    private AnouncementType type;
    private Boolean isAvailable;
    @Lob
    private String picture;
}
