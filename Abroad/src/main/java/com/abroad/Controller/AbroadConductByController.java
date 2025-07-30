package com.abroad.Controller;

import com.abroad.Entity.AbroadConductBy;
import com.abroad.Service.AbroadConductByService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadConductByController {

    @Autowired
    private AbroadConductByService abroadConductByService;

    @PostMapping("/addConductBy")
    public ResponseEntity<AbroadConductBy> addConductBy(@RequestParam String role,
                                                        @RequestParam String email,
                                                        @RequestBody AbroadConductBy conductBy) {
        return ResponseEntity.ok(abroadConductByService.addConductBy(role, email, conductBy));
    }

    @GetMapping("/getConductByById/{id}")
    public ResponseEntity<AbroadConductBy> getConductByById(@PathVariable Long id,
                                                            @RequestParam String role,
                                                            @RequestParam String email) {
        return ResponseEntity.ok(abroadConductByService.getConductByById(id, role, email));
    }

    @GetMapping("/getAllConductBy")
    public ResponseEntity<?> getAllConductBy(@RequestParam String role,
                                             @RequestParam String email) {
        return ResponseEntity.ok(abroadConductByService.getAllConductBy(role, email));
    }

    @PutMapping("/updateConductBy/{id}")
    public ResponseEntity<AbroadConductBy> updateConductBy(@PathVariable Long id,
                                                           @RequestParam String role,
                                                           @RequestParam String email,
                                                           @RequestBody AbroadConductBy conductBy) {
        return ResponseEntity.ok(abroadConductByService.updateConductBy(id, role, email, conductBy));
    }

    @DeleteMapping("/deleteConductBy/{id}")
    public ResponseEntity<?> deleteConductBy(@PathVariable Long id,
                                             @RequestParam String role,
                                             @RequestParam String email) {
        return ResponseEntity.ok(abroadConductByService.deleteConductBy(id, role, email));
    }
}
