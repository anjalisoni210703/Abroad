package com.abroad.Controller;

import com.abroad.DTO.LoginRequest;
import com.abroad.DTO.LoginResponse;
import com.abroad.Serviceimpl.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://wayabroad.in")
public class StaffController
{
    @Autowired
    private StaffService staffLoginService;

    @PostMapping("/stafflogin")
    public Mono<ResponseEntity<LoginResponse>> loginStaff(@RequestBody LoginRequest request) {
        return staffLoginService.loginStaff(request)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    LoginResponse errorResponse = new LoginResponse();
                    errorResponse.setToken(null);
                    errorResponse.setData(Map.of("error", "Login failed: " + e.getMessage()));
                    return Mono.just(ResponseEntity.status(500).body(errorResponse));
                });

    }


    @GetMapping("/permissionForStaff")
    public Map<String, Boolean> getPermission(@RequestParam String staffEmail) {
        return staffLoginService.getPermissionsByEmail(staffEmail);
    }


    @GetMapping("/permissionForBranch")
    public ResponseEntity<Map<String, Object>> getBranchPermissions(@RequestParam("branchEmail") String BranchEmail) {

        Map<String, Object> permissions = staffLoginService.getCrudPermissionForBranchtByEmail(BranchEmail);
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/permissionForAdmin")
    public ResponseEntity<Map<String, Object>> getAdminPermissions(@RequestParam("adminEmail") String adminEmail) {

        Map<String, Object> permissions = staffLoginService.getCrudPermissionForAdminByEmail(adminEmail);
        return ResponseEntity.ok(permissions);
    }
}
