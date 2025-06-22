package com.abroad.controller;

import com.abroad.entity.Registration;
import com.abroad.service.RegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class RegistrationController {
    @Autowired
    private RegistrationService service;

    @PostMapping("/createRegistration")
    public ResponseEntity<Registration> createRegistration(@RequestPart("registration") String registrationJson,
                                                           @RequestParam(value = "image", required = false) MultipartFile image,
                                                           @RequestParam String role,
                                                           @RequestParam String email) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Registration registration = mapper.readValue(registrationJson, Registration.class);
        return ResponseEntity.ok(service.createRegistration(registration, image, role, email));
    }

    @PutMapping("/updateRegistration/{id}")
    public ResponseEntity<Registration> updateRegistration(@PathVariable Long id,
                                                           @RequestPart("registration") String registrationJson,
                                                           @RequestParam(value = "image", required = false) MultipartFile image,
                                                           @RequestParam String role,
                                                           @RequestParam String email) throws JsonProcessingException {
        Registration registration = new ObjectMapper().readValue(registrationJson, Registration.class);
        return ResponseEntity.ok(service.updateRegistration(id, registration, image, role, email));
    }

    @GetMapping("/getAllRegistrations")
    public ResponseEntity<List<Registration>> getAllRegistrations(@RequestParam String role,
                                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getAllRegistrations(role, email));
    }

    @GetMapping("/getRegistrationById/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id,
                                                            @RequestParam String role,
                                                            @RequestParam String email) {
        return ResponseEntity.ok(service.getRegistrationById(id, role, email));
    }

    @DeleteMapping("/deleteRegistration/{id}")
    public ResponseEntity<String> deleteRegistration(@PathVariable Long id,
                                                     @RequestParam String role,
                                                     @RequestParam String email) {
        service.deleteRegistration(id, role, email);
        return ResponseEntity.ok("Registration deleted successfully");
    }
}
