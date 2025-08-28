package com.abroad.Controller;

import com.abroad.Entity.AbroadRegister;
import com.abroad.Service.AbroadRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")

public class AbroadRegisterController {

    @Autowired
    private AbroadRegisterService service;

    @PostMapping("/createRegister")
    public ResponseEntity<AbroadRegister> create(@RequestParam String role,
                                                 @RequestParam String email,
                                                 @RequestBody AbroadRegister register){
        return ResponseEntity.ok(service.createRegister(role,email,register));
    }

    @GetMapping("/getRegisterById/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id,
                                     @RequestParam  String role,
                                     @RequestParam String email){
        return ResponseEntity.ok(service.getById(id, role, email));
    }

    @GetMapping("/getAllRegister")
    public ResponseEntity<?> getAll(@RequestParam String role,
                                    @RequestParam String email){
        return ResponseEntity.ok(service.getAll(role,email));
    }

    @PutMapping("/updateRegister/{id}")
    public ResponseEntity<AbroadRegister> update(@PathVariable Long id,
                                                 @RequestParam String role,
                                                 @RequestParam String email,
                                                 @RequestBody AbroadRegister register){
        return ResponseEntity.ok(service.update(id, role, email, register));
    }

    @DeleteMapping("/deleteRegister/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,
                                         @RequestParam String role,
                                         @RequestParam String email) {
        service.delete(id, role, email);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
