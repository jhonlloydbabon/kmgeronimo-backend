package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.HistoryEntity;
import com.kmgeronimo.dentalclinicbackend.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/")
    public List<HistoryEntity> getAllHistory(){
        return historyService.getAllHistory();
    }
}
