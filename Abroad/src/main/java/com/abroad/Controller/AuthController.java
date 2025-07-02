package com.abroad.Controller;

import com.abroad.DTO.AuthRequest;
import com.abroad.DTO.LoginResponse;
import com.abroad.Service.AbroadUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class AuthController {

    @Autowired
    private AbroadUserService userService;

    @PostMapping("/userLogin")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/permissionForUser")
    public Map<String, Object> getPermissionsByEmail(@RequestParam String email) {
        return userService.getPermissionsByEmail(email);
    }

}
