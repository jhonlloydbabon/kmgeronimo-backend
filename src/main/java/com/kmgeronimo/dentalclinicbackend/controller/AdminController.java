package com.kmgeronimo.dentalclinicbackend.controller;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.event.RegistrationCompleteEvent;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Admin;
import com.kmgeronimo.dentalclinicbackend.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController implements ErrorController {

    @Autowired
    private AdminService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/registration")
    public ResponseEntity<ResponseMessage> registerAdmin(@RequestBody Admin admin, final HttpServletRequest request) throws Exception {
        ResponseMessage message = service.registerAdmin(admin);
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginAdmin (@RequestParam Map<String, String> requestParam){
        String username = requestParam.get("username");
        String password = requestParam.get("password");
        Map<String, Object> result = service.verifyLoginAdmin(username, password);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/")
    public List<AdminEntity> fetchDentist(){
        return service.fetchDentist();
    }
    @PostMapping("/verifyAccount")
    public ResponseEntity<String> authorizedUser(@RequestParam("token") String token) throws Exception {
        String result =service.authorizedUser(token);
        return ResponseEntity.ok().body(result);
    }
    @GetMapping("/getAdmin/{token}")
    public ResponseEntity<Optional<AdminEntity>> fetchUserInformation(@PathVariable("token") String token){
        Optional<AdminEntity> admin = service.fetchUserInformation(token);
        return ResponseEntity.ok().body(admin);
    }
    @GetMapping("/hello")
    public ResponseEntity<String> greet(){
        return ResponseEntity.ok().body("Congrats, JL");
    }

    @PostMapping("/verifyemail")
    public ResponseEntity<ResponseMessage> verifyEmail(@RequestParam("email") String adminEmail){
        ResponseMessage message = service.verifyEmail(adminEmail);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
    @PostMapping("/resetpassword/{id}")
    public ResponseEntity<ResponseMessage> resetAdminPassword(@PathVariable("id") String id){
        ResponseMessage message = service.resetAdminPassword(id);
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<ResponseMessage> changePassword(@RequestParam Map<String, String> request){
        ResponseMessage message = service.changePassword(request);
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage> updateAdminInformation(@PathVariable("id") String id, @RequestBody Admin admin){
        ResponseMessage message = service.updateAdminInformation(id, admin);
        return ResponseEntity.status(message.getStatus()).body(message);
    }

    @PostMapping("/disable")
    public ResponseEntity<ResponseMessage> disableAdmin(@RequestBody AccountDisable accountDisable){
        ResponseMessage message = service.disableAdmin(accountDisable);
        return ResponseEntity.status(message.getStatus()).body(message);
    }
}
