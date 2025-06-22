package com.abroad.controller;

import com.abroad.entity.Branch;
import com.abroad.service.BranchService;
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
public class BranchController {
    @Autowired
    private BranchService service;

    @PostMapping("/createBranch")
    public ResponseEntity<Branch> createBranch(@RequestPart("branch") String branchJson,
                                               @RequestParam(value = "image", required = false) MultipartFile image,
                                               @RequestParam String role,
                                               @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Branch branch = mapper.readValue(branchJson, Branch.class);
        return ResponseEntity.ok(service.createBranch(branch, image, role, email));
    }

    @PutMapping("/updateBranch/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id,
                                               @RequestPart("branch") String branchJson,
                                               @RequestParam(value = "image", required = false) MultipartFile image,
                                               @RequestParam String role,
                                               @RequestParam String email) throws JsonProcessingException {
        Branch branch = new ObjectMapper().readValue(branchJson, Branch.class);
        return ResponseEntity.ok(service.updateBranch(id, branch, image, role, email));
    }

    @GetMapping("/getAllBranches")
    public ResponseEntity<List<Branch>> getAllBranches(@RequestParam String role,
                                                       @RequestParam String email) {
        return ResponseEntity.ok(service.getAllBranches(role, email));
    }

    @GetMapping("/getBranchById/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        return ResponseEntity.ok(service.getBranchById(id, role, email));
    }

    @DeleteMapping("/deleteBranch/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long id,
                                               @RequestParam String role,
                                               @RequestParam String email) {
        service.deleteBranch(id, role, email);
        return ResponseEntity.ok("Branch deleted successfully");
    }
}
