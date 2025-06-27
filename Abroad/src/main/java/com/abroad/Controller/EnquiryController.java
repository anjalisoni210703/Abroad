package com.abroad.Controller;

import com.abroad.Entity.AbroadEnquiry;
import com.abroad.Service.EnquiryService;
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
public class EnquiryController {
    @Autowired
    private EnquiryService service;

    @PostMapping("/createEnquiry")
    public ResponseEntity<AbroadEnquiry> createEnquiry(@RequestPart("enquiry") String enquiryJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadEnquiry abroadEnquiry = mapper.readValue(enquiryJson, AbroadEnquiry.class);
        return ResponseEntity.ok(service.createEnquiry(abroadEnquiry, image, role, email));
    }

    @PutMapping("/updateEnquiry/{id}")
    public ResponseEntity<AbroadEnquiry> updateEnquiry(@PathVariable Long id,
                                                       @RequestPart("enquiry") String enquiryJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {
        AbroadEnquiry abroadEnquiry = new ObjectMapper().readValue(enquiryJson, AbroadEnquiry.class);
        return ResponseEntity.ok(service.updateEnquiry(id, abroadEnquiry, image, role, email));
    }

    @GetMapping("/getAllEnquiries")
    public ResponseEntity<List<AbroadEnquiry>> getAllEnquiries(@RequestParam String role,
                                                               @RequestParam String email) {
        return ResponseEntity.ok(service.getAllEnquiries(role, email));
    }

    @GetMapping("/getEnquiryById/{id}")
    public ResponseEntity<AbroadEnquiry> getEnquiryById(@PathVariable Long id,
                                                        @RequestParam String role,
                                                        @RequestParam String email) {
        return ResponseEntity.ok(service.getEnquiryById(id, role, email));
    }

    @DeleteMapping("/deleteEnquiry/{id}")
    public ResponseEntity<String> deleteEnquiry(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deleteEnquiry(id, role, email);
        return ResponseEntity.ok("Enquiry deleted successfully");
    }
}
