package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import com.kmgeronimo.dentalclinicbackend.entity.PasswordVerificationToken;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.entity.VerificationToken;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Admin;
import com.kmgeronimo.dentalclinicbackend.repository.AdminRepository;
import com.kmgeronimo.dentalclinicbackend.repository.PasswordRecoveryTokenRepository;
import com.kmgeronimo.dentalclinicbackend.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private PasswordRecoveryTokenRepository passwordRepository;

    @Override
    public ResponseMessage registerAdmin(Admin admin) throws Exception {
        try{
            AdminEntity adminEntity = new AdminEntity();
            BeanUtils.copyProperties(admin, adminEntity);
            adminEntity.setRole("STAFF");
            adminEntity.setPassword(passwordEncoder.encode(admin.getPassword()));
            adminEntity.setEnabled(true);
            repository.save(adminEntity);
            String token = UUID.randomUUID().toString();
            saveVerificationTokenForAdmin(adminEntity, token);
            return new ResponseMessage(HttpStatus.OK, "Register successfully!");
        }catch (Exception ex){ throw new Exception("Can't register information"); }
    }

    @Override
    public Map<String, Object> verifyLoginAdmin(String username, String password) {

        AdminEntity adminEntity = repository.findByUsername(username);
        if(Objects.isNull(adminEntity)){
            return new HashMap<>(){{ put("status", false); put("message", "Invalid username or password!"); }};
        }
        if(!adminEntity.isEnabled()){
            return new HashMap<>(){{ put("status", false); put("message", "Account is blocked by the server!"); }};
        }
        if(!passwordEncoder.matches(password,adminEntity.getPassword())){
            return new HashMap<>(){{ put("status", false); put("message", "Password not recognized"); }};
        }
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = verificationTokenRepository.findByAdminEntity(adminEntity);
        if(!Objects.isNull(verificationToken)){
            verificationToken.setToken(token);
            verificationTokenRepository.save(verificationToken);

        }else{
            saveVerificationTokenForAdmin(adminEntity, token);
        }
        return new HashMap<>(){{ put("status", true); put("message", verificationToken.getToken()); }};
    }


    @Override
    public String authorizedUser(String id) throws Exception {
        try{
            Optional<VerificationToken> verificationToken = Optional.ofNullable(verificationTokenRepository.findByToken(id));
            if(verificationToken.isPresent()) return "valid";
            return "Not valid";
        }catch (Exception ex){ throw new Exception("Invalid Token"); }
    }

    @Override
    public Optional<AdminEntity> fetchUserInformation(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        Optional<AdminEntity> adminEntity = repository.findById(verificationToken.getAdminEntity().getAdminId());
        return adminEntity;
    }

    @Override
    public ResponseMessage verifyEmail(String adminEmail) {
        AdminEntity adminEntity = repository.findByEmail(adminEmail);
        if(Objects.isNull(adminEntity)){
            return new ResponseMessage(HttpStatus.NOT_FOUND, "Email doesn't exist!");
        }
        if(!adminEntity.isEnabled()){
            return new ResponseMessage(HttpStatus.NOT_FOUND, "You're account is already blocked, kindly contact the dental clinic.");
        }
        String token = UUID.randomUUID().toString();
        PasswordVerificationToken verificationToken = new PasswordVerificationToken(adminEntity, token);
        passwordRepository.save(verificationToken);

        sendAccountRecovery(token, verificationToken.getAdminEntity().getLastname(), verificationToken.getAdminEntity().getEmail());
        return new ResponseMessage(HttpStatus.OK, "Message sent!");
    }

    @Override
    public ResponseMessage resetAdminPassword(String id) {
        PasswordVerificationToken verificationToken = passwordRepository.findByToken(id);
        if(Objects.isNull(verificationToken)){
            return new ResponseMessage(HttpStatus.NOT_FOUND, "Invalid token!");
        }
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <=0 ){
            passwordRepository.delete(verificationToken);
            return new ResponseMessage(HttpStatus.REQUEST_TIMEOUT, "Token expired!");
        }

        return new ResponseMessage(HttpStatus.OK, "verified");
    }

    @Override
    public ResponseMessage changePassword(Map<String, String> request) {
        PasswordVerificationToken token = passwordRepository.findByToken(request.get("token"));
        AdminEntity adminEntity = repository.findById(token.getAdminEntity().getAdminId()).get();
        adminEntity.setPassword(passwordEncoder.encode(request.get("password")));
        repository.save(adminEntity);
        passwordRepository.delete(token);
        return new ResponseMessage(HttpStatus.OK, "Saved Password!");
    }

    @Override
    public List<AdminEntity> fetchDentist() {
        return repository.findAll();
    }

    @Override
    public ResponseMessage updateAdminInformation(String id, Admin admin) {
        AdminEntity adminEntity = repository.findById(id).get();
        adminEntity.setFirstname(admin.getFirstname());
        adminEntity.setMiddlename(admin.getMiddlename());
        adminEntity.setLastname(admin.getLastname());
        adminEntity.setAddress(admin.getAddress());
        adminEntity.setBirthday(admin.getBirthday());
        adminEntity.setContactNumber(adminEntity.getContactNumber());
        adminEntity.setEmail(adminEntity.getEmail());
        adminEntity.setGender(admin.getGender());
        adminEntity.setProfile(admin.getProfile());
        repository.save(adminEntity);
        return new ResponseMessage(HttpStatus.OK, "Update successfully");
    }

    @Override
    public ResponseMessage disableAdmin(AccountDisable accountDisable) {
        AdminEntity adminEntity = repository.findById(accountDisable.getId()).get();
        adminEntity.setEnabled(accountDisable.getVerified());
        repository.save(adminEntity);
        return new ResponseMessage(HttpStatus.OK, "Disable Successfully!");
    }

    @Override
    public void saveVerificationTokenForAdmin(AdminEntity adminEntity, String token) {
        VerificationToken verificationToken = new VerificationToken(adminEntity, token);
        verificationTokenRepository.save(verificationToken);
    }

    public void sendAccountRecovery(String token, String lastname , String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kmgeronimo.dentalclinic@gmail.com");
        message.setTo(email);
        message.setSubject("Account Recovery");
        message.setText(messageBody(token, lastname));
        sender.send(message);
    }

    public String messageBody(String token, String lastname){
        return "Hello, Mr/Mrs"+lastname+
                "\nTo recover your account kindly click the link below"
                +"\nhttp://localhost:3000/recoveraccount/resetpassword/"+token;
    }
}
