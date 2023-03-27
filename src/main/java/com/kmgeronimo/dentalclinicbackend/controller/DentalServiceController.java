package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.DentalServiceEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.DentalService;
import com.kmgeronimo.dentalclinicbackend.service.DentalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/services")
public class DentalServiceController {
    @Autowired
    private DentalServiceService service;

    @PostMapping("/")
    public ResponseEntity<ResponseMessage> addServices(@RequestBody DentalService dentalService){
        ResponseMessage message = service.addServices(dentalService);
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @GetMapping("/")
    public List<DentalServiceEntity> fecthAllDentalServices(){
        return service.fecthAllDentalServices();
    }
}
