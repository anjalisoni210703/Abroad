package com.abroad.Controller;

import com.abroad.Entity.AbroadUser;
import com.abroad.Service.AbroadUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadUserController {

    @Autowired
    private AbroadUserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<AbroadUser> createUser(@RequestBody AbroadUser user,
                                                 @RequestParam String email) {
        return ResponseEntity.ok(userService.createUser(user, email));
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<AbroadUser> updateUser(@PathVariable Long id, @RequestBody AbroadUser user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<AbroadUser> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<AbroadUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/getBranchCodeByUserEmail")
    public ResponseEntity<String> getBranchCodeByUserEmail(@RequestParam String email) {
        AbroadUser user = userService
                .getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return ResponseEntity.ok(user.getBranchCode());
    }

}