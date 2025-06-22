package com.abroad.controller;

import com.abroad.entity.College;
import com.abroad.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.abroad.entity.College;
import com.abroad.service.CollegeService;
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

public class CollegeController {
    @Autowired
    private CollegeService service;

    @PostMapping("/createCollege")
    public ResponseEntity<College> createCollege(@RequestPart("college") String collegeJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        College college = mapper.readValue(collegeJson, College.class);
        return ResponseEntity.ok(service.createCollege(college, image, role, email));
    }

    @PutMapping("/updateCollege/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id,
                                                 @RequestPart("college") String collegeJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        College college = new ObjectMapper().readValue(collegeJson, College.class);
        return ResponseEntity.ok(service.updateCollege(id, college, image, role, email));
    }

    @GetMapping("/getAllColleges")
    public ResponseEntity<List<College>> getAllColleges(@RequestParam String role,
                                                        @RequestParam String email) {
        return ResponseEntity.ok(service.getAllColleges(role, email));
    }

    @GetMapping("/getCollegeById/{id}")
    public ResponseEntity<College> getCollegeById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getCollegeById(id, role, email));
    }

    @DeleteMapping("/deleteCollege/{id}")
    public ResponseEntity<String> deleteCollege(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deleteCollege(id, role, email);
        return ResponseEntity.ok("College deleted successfully");
    }
}
