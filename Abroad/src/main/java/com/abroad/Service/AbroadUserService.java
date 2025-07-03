package com.abroad.Service;

import com.abroad.DTO.AuthRequest;
import com.abroad.DTO.LoginResponse;
import com.abroad.Entity.AbroadUser;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AbroadUserService {
    AbroadUser createUser(AbroadUser user, String email);
    AbroadUser updateUser(Long id, AbroadUser user);
    AbroadUser getUserById(Long id);
    List<AbroadUser> getAllUsers();
    void deleteUser(Long id);

    LoginResponse login(AuthRequest loginRequest);
    Map<String, Object> getPermissionsByEmail(String email);
    Optional<AbroadUser> getUserByEmail(String email);

}
