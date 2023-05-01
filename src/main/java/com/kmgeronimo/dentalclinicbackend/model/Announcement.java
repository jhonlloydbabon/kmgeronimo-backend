package com.kmgeronimo.dentalclinicbackend.model;

import com.kmgeronimo.dentalclinicbackend.enums.AnouncementType;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announcement {
    private String title;
    private String description;
    private String type;
    private String picture;
}
