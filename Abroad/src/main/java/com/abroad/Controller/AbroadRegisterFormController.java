package com.abroad.Controller;

import com.abroad.Entity.AbroadRegisterForm;
import com.abroad.Service.AbroadRegisterFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AbroadRegisterFormController {

    @Autowired
    private AbroadRegisterFormService service;

    @PostMapping("/createRegisterForm")
    public ResponseEntity<AbroadRegisterForm> createForm(@RequestBody AbroadRegisterForm form
//                                                         @RequestParam String role,
//                                                         @RequestParam String email)
    ){
        AbroadRegisterForm created = service.createRegisterForm(form);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/updateRegisterForm/{id}")
    public ResponseEntity<AbroadRegisterForm> updateForm(@PathVariable int id,
                                                         @RequestBody AbroadRegisterForm form,
                                                         @RequestParam String role,
                                                         @RequestParam String email) {
        AbroadRegisterForm updated = service.updateRegisterForm(id, form, role, email);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/getAllRegisterForms")
    public ResponseEntity<List<AbroadRegisterForm>> getAll(@RequestParam String role,
                                                           @RequestParam String email) {
        return ResponseEntity.ok(service.getAllRegisterForms(role, email));
    }

    @GetMapping("/getRegisterFormById/{id}")
    public ResponseEntity<AbroadRegisterForm> getById(@PathVariable int id,
                                                      @RequestParam String role,
                                                      @RequestParam String email) {
        return ResponseEntity.ok(service.getRegisterFormById(id, role, email));
    }

    @DeleteMapping("/deleteRegisterForm/{id}")
    public ResponseEntity<String> delete(@PathVariable int id,
                                         @RequestParam String role,
                                         @RequestParam String email) {
        service.deleteRegisterForm(id, role, email);
        return ResponseEntity.ok("Register form deleted successfully.");
    }
}
