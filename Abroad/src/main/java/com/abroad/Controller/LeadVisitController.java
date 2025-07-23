package com.abroad.Controller;

import com.abroad.Entity.AbroadLeadVisit;
import com.abroad.Serviceimpl.LeadVisitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class LeadVisitController {

    @Autowired
    private LeadVisitServiceImpl leadVisitService;

    @PostMapping("/addLeadVisit/{enquiry_id}")
    public ResponseEntity<AbroadLeadVisit> addVisit(@PathVariable Long enquiry_id,
                                                    @RequestParam String role,
                                                    @RequestParam String email,
                                                    @RequestParam String remark,
                                                    @RequestParam String status,
                                                    @RequestParam String visitCount) {
        return ResponseEntity.ok(leadVisitService.addVisit(enquiry_id,role,email ,remark, visitCount, status));
    }

    @GetMapping("/getAllVisits")
    public List<AbroadLeadVisit> getAllVisits(@RequestParam String role,
                                              @RequestParam String email){
        return leadVisitService.getAllVisits(role,email);
    }

    @GetMapping("/getVisitById/{id}")
    public ResponseEntity<AbroadLeadVisit> getVisitById(@PathVariable Long id,
                                                        @RequestParam String role,
                                                        @RequestParam String email){
        return ResponseEntity.ok(leadVisitService.getVisitById(id,role,email));
    }
    @DeleteMapping("/deleteVisit/{id}")
    public ResponseEntity<?> deleteVisit(@PathVariable Long id,
                                         @RequestParam String role,
                                         @RequestParam String email){
        return ResponseEntity.ok(leadVisitService.deleteVisit(id,role,email));
    }
    @GetMapping("/getVisitByLeadId/{id}")
    public ResponseEntity<?> getLeadById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email){
        return ResponseEntity.ok(leadVisitService.getVisitByLeadId(id,role,email));
    }
}
