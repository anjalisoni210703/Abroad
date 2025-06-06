package com.abroad.controller;

import com.abroad.dto.EnquiryDTO;
import com.abroad.dto.EnquiryFilterDTO;
import com.abroad.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EnquiryController {
    @Autowired
    private EnquiryService enquiryService;

    @PostMapping("/getAllEnquiries")
    public ResponseEntity<Page<EnquiryDTO>> getAllEnquiries(
            @RequestBody(required = false) EnquiryFilterDTO filterDTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EnquiryDTO> enquiries = enquiryService.getAllEnquiries(filterDTO, pageable);
        return ResponseEntity.ok(enquiries);
    }
    @PostMapping("/addEnquiry")
    public ResponseEntity<EnquiryDTO> addEnquiry(@RequestBody EnquiryDTO enquiryDTO) {
        EnquiryDTO savedEnquiry = enquiryService.saveEnquiry(enquiryDTO);
        return ResponseEntity.ok(savedEnquiry);
    }
}
