package com.kmgeronimo.dentalclinicbackend.service;

import com.kmgeronimo.dentalclinicbackend.entity.AdminEntity;
import com.kmgeronimo.dentalclinicbackend.entity.ResponseMessage;
import com.kmgeronimo.dentalclinicbackend.model.AccountDisable;
import com.kmgeronimo.dentalclinicbackend.model.Admin;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdminService {
    ResponseMessage registerAdmin(Admin admin) throws Exception;

    Map<String, Object> verifyLoginAdmin(String username, String password);

    void saveVerificationTokenForAdmin(AdminEntity adminEntity, String token);

    String authorizedUser(String id) throws Exception;

    Optional<AdminEntity> fetchUserInformation(String token);

    ResponseMessage verifyEmail(String adminEmail);

    ResponseMessage resetAdminPassword(String id);


    ResponseMessage changePassword(Map<String, String> request);

    List<AdminEntity> fetchDentist();

    ResponseMessage updateAdminInformation(String id, Admin admin);

    ResponseMessage disableAdmin(AccountDisable accountDisable);
}
