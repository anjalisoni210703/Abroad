package com.abroad.Controller;

import com.abroad.Entity.AbroadCollege;
import com.abroad.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class CollegeController {
    @Autowired
    private CollegeService service;

    @PostMapping("/createCollege")
    public ResponseEntity<AbroadCollege> createCollege(@RequestPart("college") String collegeJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email,
                                                       @RequestParam Long universityId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadCollege abroadCollege = mapper.readValue(collegeJson, AbroadCollege.class);
        return ResponseEntity.ok(service.createCollege(abroadCollege, image, role, email, universityId));
    }

    @PutMapping("/updateCollege/{id}")
    public ResponseEntity<AbroadCollege> updateCollege(@PathVariable Long id,
                                                       @RequestPart("college") String collegeJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {
        AbroadCollege abroadCollege = new ObjectMapper().readValue(collegeJson, AbroadCollege.class);
        return ResponseEntity.ok(service.updateCollege(id, abroadCollege, image, role, email));
    }

    @GetMapping("/getAllColleges")
    public ResponseEntity<List<AbroadCollege>> getAllColleges(@RequestParam String role,
                                                              @RequestParam String email,
                                                              @RequestParam String branchCode,
                                                              @RequestParam(required = false) Long universityId) {
        return ResponseEntity.ok(service.getAllColleges(role, email, branchCode, universityId));
    }

    @GetMapping("/getCollegeById/{id}")
    public ResponseEntity<AbroadCollege> getCollegeById(@PathVariable Long id,
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
