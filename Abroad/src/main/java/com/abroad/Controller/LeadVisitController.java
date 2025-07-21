package com.abroad.Controller;

import com.abroad.Entity.AbroadLead;
import com.abroad.Entity.LeadVisit;
import com.abroad.Serviceimpl.LeadVisitServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeadVisitController {

    @Autowired
    private LeadVisitServiceImpl leadVisitService;

    @PostMapping("addLeadVisit/{lead_id}")
    public ResponseEntity<LeadVisit> addVisit(@PathVariable Long lead_id,
                                              @RequestParam String role,
                                              @RequestParam String email,
                                              @RequestParam String remark,
                                              @RequestParam String status,
                                              @RequestParam String visitCount) {
        return ResponseEntity.ok(leadVisitService.addVisit(lead_id,role,email ,remark, visitCount, status));
    }

    @GetMapping("getAllVisits")
    public List<LeadVisit> getAllVisits(@RequestParam String role,
                                        @RequestParam String email){
        return leadVisitService.getAllVisits(role,email);
    }

    @GetMapping("getVisitById/{id}")
    public ResponseEntity<LeadVisit> getVisitById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email){
        return ResponseEntity.ok(leadVisitService.getVisitById(id,role,email));
    }
    @DeleteMapping("deleteVisit/{id}")
    public ResponseEntity<?> deleteVisit(@PathVariable Long id,
                                         @RequestParam String role,
                                         @RequestParam String email){
        return ResponseEntity.ok(leadVisitService.deleteVisit(id,role,email));
    }
    @GetMapping("getVisitByLeadId/{id}")
    public ResponseEntity<?> getLeadById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email){
        return ResponseEntity.ok(leadVisitService.getVisitByLeadId(id,role,email));
    }
}
