package com.abroad.Controller;

import com.abroad.Entity.AbroadEnquiry;
import com.abroad.Service.EnquiryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class EnquiryController {
    @Autowired
    private EnquiryService service;

//
@PostMapping("/createEnquiry")
public ResponseEntity<AbroadEnquiry> createEnquiry(@RequestPart("enquiry") String enquiryJson,
                                                   @RequestParam(value = "image", required = false) MultipartFile image,
                                                   @RequestParam(value = "document1", required = false) MultipartFile document1,
                                                   @RequestParam(value = "document2", required = false) MultipartFile document2,
                                                   @RequestParam String role,
                                                   @RequestParam String email,
                                                   @RequestParam Long continentId,
                                                   @RequestParam Long countryId,
                                                   @RequestParam Long universityId,
//                                                   @RequestParam Long courseId,
                                                   @RequestParam Long stateId,
                                                   @RequestParam Long cityId,
                                                   @RequestParam Long collegeId) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    AbroadEnquiry abroadEnquiry = mapper.readValue(enquiryJson, AbroadEnquiry.class);

    AbroadEnquiry created = service.createEnquiry(abroadEnquiry, image, document1, document2,
            role, email, continentId, countryId, universityId, stateId, cityId, collegeId);

    return ResponseEntity.ok(created);
}

//    @PutMapping("/updateEnquiry/{id}")
//    public ResponseEntity<AbroadEnquiry> updateEnquiry(@PathVariable Long id,
//                                                       @RequestPart("enquiry") String enquiryJson,
//                                                       @RequestParam(value = "image", required = false) MultipartFile image,
//                                                       @RequestParam ( required = false) Map<String, MultipartFile> updateDoc,
//                                                       @RequestParam String role,
//                                                       @RequestParam String email) throws JsonProcessingException {
//        AbroadEnquiry abroadEnquiry = new ObjectMapper().readValue(enquiryJson, AbroadEnquiry.class);
//        return ResponseEntity.ok(service.updateEnquiry(id, abroadEnquiry, image, updateDoc,role, email));
//    }

    @PutMapping("/updateEnquiry/{id}")
    public ResponseEntity<AbroadEnquiry> updateEnquiry(@PathVariable Long id,
                                                       @RequestPart("enquiry") String enquiryJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam(value = "document1", required = false) MultipartFile document1,
                                                       @RequestParam(value = "document2", required = false) MultipartFile document2,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {

        AbroadEnquiry abroadEnquiry = new ObjectMapper().readValue(enquiryJson, AbroadEnquiry.class);

        return ResponseEntity.ok(service.updateEnquiry(id, abroadEnquiry, image, document1, document2, role, email));
    }

    //for super admin
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

    @PostMapping("/filter")
    public Page<AbroadEnquiry> filterEnquiries(
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String stream,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String enquiryDateFilter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String branchCode,
            @RequestParam(required = false) String applyFor,
            @RequestParam(required = false) String conductBy,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.filterEnquiries(
                continent, country, stream, course, status,
                branchCode, role, email, fullName,
                enquiryDateFilter, startDate, endDate, applyFor, conductBy,page, size
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<AbroadEnquiry>> searchEnquiries(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long phoneNo) {

        List<AbroadEnquiry> enquiries = service.getAllEnquiryDataByIdOrNameOrEmailOrPhone(id, name, email, phoneNo);
        return ResponseEntity.ok(enquiries);
    }


    /// we need inquiry by Day and Month Yearwise  wise count *******
    @GetMapping("/inquiry-counts")
    public ResponseEntity<Map<String, Object>> getInquiryCounts(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) Integer month) {

        Map<String, Object> response;

        if (year != null && month != null) {
            // Case 1: Year + Month → Day-wise breakdown
            response = service.getDailyInquiryCountsWithTotal(year, month);
        } else if (startYear != null) {
            // Case 2: Year only → Month-wise breakdown
            response = service.getMonthlyInquiryCountsWithTotal(startYear,endYear);
        } else {
            // Case 3: Default → All years breakdown
            response = service.getYearlyInquiryCounts();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllEnquiryByBranchCode/{branchCode}")
    public ResponseEntity<List<AbroadEnquiry>> getAllByBranchCode(
            @PathVariable String branchCode,
            @RequestParam String role,
            @RequestParam String email) {
        return ResponseEntity.ok(service.getAllEnquiryByBranchCode(role, email, branchCode));
    }

    @GetMapping("/getAllEnquiryByCreatedByEmail/{createdByEmail}")
    public ResponseEntity<List<AbroadEnquiry>> getAllByCreatedByEmail(
            @PathVariable String createdByEmail,
            @RequestParam String role,
            @RequestParam String email) {
        return ResponseEntity.ok(service.getAllEnquiryByCreatedByEmail(role, email, createdByEmail));
    }



}
