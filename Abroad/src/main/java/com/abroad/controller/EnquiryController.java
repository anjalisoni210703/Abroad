package com.abroad.controller;

import com.abroad.dto.EnquiryDTO;
import com.abroad.dto.EnquiryFilterDTO;
import com.abroad.entity.Enquiry;
import com.abroad.service.EnquiryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Enquiry> createEnquiry(@RequestPart("enquiry") String enquiryJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Enquiry enquiry = mapper.readValue(enquiryJson, Enquiry.class);
        return ResponseEntity.ok(service.createEnquiry(enquiry, image, role, email));
    }

    @PutMapping("/updateEnquiry/{id}")
    public ResponseEntity<Enquiry> updateEnquiry(@PathVariable Long id,
                                                 @RequestPart("enquiry") String enquiryJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        Enquiry enquiry = new ObjectMapper().readValue(enquiryJson, Enquiry.class);
        return ResponseEntity.ok(service.updateEnquiry(id, enquiry, image, role, email));
    }

    @GetMapping("/getAllEnquiries")
    public ResponseEntity<List<Enquiry>> getAllEnquiries(@RequestParam String role,
                                                         @RequestParam String email) {
        return ResponseEntity.ok(service.getAllEnquiries(role, email));
    }

    @GetMapping("/getEnquiryById/{id}")
    public ResponseEntity<Enquiry> getEnquiryById(@PathVariable Long id,
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
