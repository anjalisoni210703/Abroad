package com.abroad.controller;

import com.abroad.dto.SuperAdminLoginRequest;
import com.abroad.service.JwtService;
import com.abroad.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SuperAdminAuthController {
    @Autowired
    private SuperAdminService superAdminService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody SuperAdminLoginRequest loginRequest) {
        return superAdminService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())
                .map(admin -> {
                    String token = jwtService.generateToken(admin.getEmail());
                    return ResponseEntity.ok(Map.of(
                            "message", "Login Successful",
                            "token", token,
                            "name", admin.getName()
                    ));
                })
                .orElse(ResponseEntity.status(401).body(
                        Map.of("error", "Invalid Credentials")
                ));
    }


}
