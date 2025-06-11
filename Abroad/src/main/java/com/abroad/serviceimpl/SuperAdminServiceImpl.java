package com.abroad.serviceimpl;

import com.abroad.entity.SuperAdmin;
import com.abroad.repository.SuperAdminRepository;
import com.abroad.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {
    @Autowired
    private SuperAdminRepository repository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public SuperAdmin saveSuperAdmin(SuperAdmin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return repository.save(admin);
    }

    @Override
    public Optional<SuperAdmin> authenticate(String email, String rawPassword) {
        Optional<SuperAdmin> adminOpt = repository.findByEmail(email);
        if (adminOpt.isPresent() && passwordEncoder.matches(rawPassword, adminOpt.get().getPassword())) {
            return adminOpt;
        }
        return Optional.empty();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        SuperAdmin admin = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return User.builder()
                .username(admin.getEmail())
                .password(admin.getPassword())
                .authorities(Collections.emptyList()) // or .roles("SUPERADMIN")
                .build();
    }
}
