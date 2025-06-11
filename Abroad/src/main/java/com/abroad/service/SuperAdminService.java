package com.abroad.service;

import com.abroad.entity.SuperAdmin;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface SuperAdminService extends UserDetailsService {
    SuperAdmin saveSuperAdmin(SuperAdmin admin);
    Optional<SuperAdmin> authenticate(String email, String rawPassword);
}
