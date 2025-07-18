package com.abroad.Controller;

import com.abroad.Entity.AbroadLead;
import com.abroad.Service.LeadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping("/createLead")
    public ResponseEntity<AbroadLead> createLead(@RequestPart("lead") String leadJson,
                                                 @RequestParam(required = false) Long continentId,
                                                 @RequestParam(required = false) Long countryId,
                                                 @RequestParam(required = false) Long courseId) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadLead lead = mapper.readValue(leadJson, AbroadLead.class);
        return ResponseEntity.ok(leadService.createLead(lead, continentId, countryId, courseId));
    }

    @GetMapping("/getAllLeads")
    public ResponseEntity<List<AbroadLead>> getAllLeads(@RequestParam String role,
                                                        @RequestParam String email) {
        return ResponseEntity.ok(leadService.getAllLeads(role, email));
    }

    @GetMapping("/getLeadById/{id}")
    public ResponseEntity<AbroadLead> getLeadById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(leadService.getLeadById(id, role, email));
    }

    @PutMapping("/updateLead/{id}")
    public ResponseEntity<AbroadLead> updateLead(@PathVariable Long id,
                                                 @RequestPart("lead") String leadJson,
                                                 @RequestParam(required = false) Long continentId,
                                                 @RequestParam(required = false) Long countryId,
                                                 @RequestParam(required = false) Long courseId,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadLead lead = mapper.readValue(leadJson, AbroadLead.class);
        return ResponseEntity.ok(leadService.updateLead(id, lead, continentId, countryId, courseId, role, email));
    }

    @DeleteMapping("/deleteLead/{id}")
    public ResponseEntity<String> deleteLead(@PathVariable Long id,
                                             @RequestParam String role,
                                             @RequestParam String email) {
        leadService.deleteLead(id, role, email);
        return ResponseEntity.ok("Lead deleted successfully");
    }
}