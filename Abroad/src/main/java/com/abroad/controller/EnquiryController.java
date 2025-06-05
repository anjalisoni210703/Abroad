package com.abroad.controller;

import com.abroad.dto.EnquiryDTO;
import com.abroad.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EnquiryController {
    @Autowired
    private EnquiryService enquiryService;

    @GetMapping("/getAllEnquiries")
    public ResponseEntity<List<EnquiryDTO>> getAllEnquiries() {
        return ResponseEntity.ok(enquiryService.getAllEnquiries());
    }
    @PostMapping("/addEnquiry")
    public ResponseEntity<EnquiryDTO> addEnquiry(@RequestBody EnquiryDTO enquiryDTO) {
        EnquiryDTO savedEnquiry = enquiryService.saveEnquiry(enquiryDTO);
        return ResponseEntity.ok(savedEnquiry);
    }
}
