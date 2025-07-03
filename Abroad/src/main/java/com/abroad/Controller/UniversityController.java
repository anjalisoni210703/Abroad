package com.abroad.Controller;

import com.abroad.Entity.AbroadUniversity;
import com.abroad.Service.UniversityService;
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
@CrossOrigin(origins = "https://wayabroad.in")
public class UniversityController {
    @Autowired
    private UniversityService service;

    @PostMapping("/createUniversity")
    public ResponseEntity<AbroadUniversity> createUniversity(@RequestPart("university") String universityJson,
                                                             @RequestParam(value = "image", required = false) MultipartFile image,
                                                             @RequestParam String role,
                                                             @RequestParam String email,
                                                             @RequestParam Long countryId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadUniversity abroadUniversity = mapper.readValue(universityJson, AbroadUniversity.class);
        return ResponseEntity.ok(service.createUniversity(abroadUniversity, image, role, email, countryId));
    }

    @PutMapping("/updateUniversity/{id}")
    public ResponseEntity<AbroadUniversity> updateUniversity(@PathVariable Long id,
                                                             @RequestPart("university") String universityJson,
                                                             @RequestParam(value = "image", required = false) MultipartFile image,
                                                             @RequestParam String role,
                                                             @RequestParam String email) throws JsonProcessingException {

        AbroadUniversity abroadUniversity = new ObjectMapper().readValue(universityJson, AbroadUniversity.class);
        return ResponseEntity.ok(service.updateUniversity(id, abroadUniversity, image, role, email));
    }

    @GetMapping("/getAllUniversities")
    public ResponseEntity<List<AbroadUniversity>> getAllUniversities(@RequestParam String role,
                                                                     @RequestParam String email,
                                                                     @RequestParam String branchCode,
                                                                     @RequestParam(required = false) Long countryId) {
        return ResponseEntity.ok(service.getAllUniversities(role, email, branchCode, countryId));
    }

    @GetMapping("/getUniversityById/{id}")
    public ResponseEntity<AbroadUniversity> getUniversityById(@PathVariable Long id,
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
