package com.abroad.controller;

import com.abroad.entity.University;
import com.abroad.service.UniversityService;
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
public class UniversityController {
    @Autowired
    private UniversityService service;

    @PostMapping("/createUniversity")
    public ResponseEntity<University> createUniversity(@RequestPart("university") String universityJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        University university = mapper.readValue(universityJson, University.class);
        return ResponseEntity.ok(service.createUniversity(university, image, role, email));
    }

    @PutMapping("/updateUniversity/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable Long id,
                                                       @RequestPart("university") String universityJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {

        University university = new ObjectMapper().readValue(universityJson, University.class);
        return ResponseEntity.ok(service.updateUniversity(id, university, image, role, email));
    }

    @GetMapping("/getAllUniversities")
    public ResponseEntity<List<University>> getAllUniversities(@RequestParam String role,
                                                               @RequestParam String email) {
        return ResponseEntity.ok(service.getAllUniversities(role, email));
    }

    @GetMapping("/getUniversityById/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable Long id,
                                                        @RequestParam String role,
                                                        @RequestParam String email) {
        return ResponseEntity.ok(service.getUniversityById(id, role, email));
    }

    @DeleteMapping("/deleteUniversity/{id}")
    public ResponseEntity<String> deleteUniversity(@PathVariable Long id,
                                                   @RequestParam String role,
                                                   @RequestParam String email) {
        service.deleteUniversity(id, role, email);
        return ResponseEntity.ok("University deleted successfully");
    }
}
