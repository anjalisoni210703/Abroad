package com.abroad.Service;

import com.abroad.DTO.AuthRequest;
import com.abroad.DTO.LoginResponse;
import com.abroad.Entity.AbroadUser;

import java.util.List;
import java.util.Map;

public interface AbroadUserService {
    AbroadUser createUser(AbroadUser user);
    AbroadUser updateUser(Long id, AbroadUser user);
    AbroadUser getUserById(Long id);
    List<AbroadUser> getAllUsers();
    void deleteUser(Long id);

    LoginResponse login(AuthRequest loginRequest);
    Map<String, Object> getPermissionsByEmail(String email);
}
