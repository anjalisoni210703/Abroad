package com.abroad.Controller;

import com.abroad.Entity.AbroadBecomePartner;
import com.abroad.Service.AbroadBecomePartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadBecomePartnerController {

    @Autowired
    private AbroadBecomePartnerService service;

    @PostMapping("/createPartner")
    public ResponseEntity<AbroadBecomePartner> createPartner(@RequestBody AbroadBecomePartner partner) {
        return ResponseEntity.ok(service.createPartner(partner));
    }

    @GetMapping("/getAllPartners")
    public ResponseEntity<List<AbroadBecomePartner>> getAllPartners(@RequestParam String role,
                                                                    @RequestParam String email) {
        return ResponseEntity.ok(service.getAllPartners(role, email));
    }

    @GetMapping("/getPartnerById/{id}")
    public ResponseEntity<AbroadBecomePartner> getPartnerById(@PathVariable Long id,
                                                              @RequestParam String role,
                                                              @RequestParam String email) {
        return ResponseEntity.ok(service.getPartnerById(id, role, email));
    }

    @PutMapping("/updatePartner/{id}")
    public ResponseEntity<AbroadBecomePartner> updatePartner(@PathVariable Long id,
                                                             @RequestBody AbroadBecomePartner partner,
                                                             @RequestParam String role,
                                                             @RequestParam String email) {
        return ResponseEntity.ok(service.updatePartner(id, partner, role, email));
    }

    @DeleteMapping("/deletePartner/{id}")
    public ResponseEntity<String> deletePartner(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deletePartner(id, role, email);
        return ResponseEntity.ok("Partner deleted successfully");
    }
}