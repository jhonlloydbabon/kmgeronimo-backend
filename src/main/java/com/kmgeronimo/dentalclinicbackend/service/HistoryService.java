package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.HistoryEntity;

import java.util.List;

public interface HistoryService {
    List<HistoryEntity> getAllHistory();
}
