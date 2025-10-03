package com.abroad.Controller;

import com.abroad.Entity.AbroadBecomePartner;
import com.abroad.Service.AbroadBecomePartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadBecomePartnerController {

    @Autowired
    private AbroadBecomePartnerService service;

    @PostMapping("/createPartner")
    public ResponseEntity<AbroadBecomePartner> createPartner(@RequestBody AbroadBecomePartner partner) {
        return ResponseEntity.ok(service.createPartner(partner));
    }

    @PostMapping("/getAllPartners")
    public ResponseEntity<?> getAllPartners(@RequestParam String role,
                                                                    @RequestParam String email,
                                                                    @RequestParam int page,
                                                                    @RequestParam int size,
                                                                    @RequestBody AbroadBecomePartner partner) {
        return ResponseEntity.ok(service.getAllPartners(role, email,page,size,partner));
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

    @PostMapping("/uploadPdf/{id}")
    public ResponseEntity<?> uploadPdf(@PathVariable Long id,
                                       @RequestParam String role,
                                       @RequestParam String email,
                                       @RequestParam("contract") MultipartFile contract,
                                       @RequestParam("commission") MultipartFile commission,
                                       @RequestParam("pan") MultipartFile pan,
                                       @RequestParam("gst") MultipartFile gst) throws IOException {

            return ResponseEntity.ok(service.UploadPdf(id, role, email, contract, commission, gst,pan));

    }

    @PostMapping("/filterBecomePartner")
    public Page<AbroadBecomePartner> filterPartners(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String businessEmail,
            @RequestParam(required = false) String instituteType,
            @RequestParam(required = false) String contractType,
            @RequestParam(required = false) String conductedBy,
            @RequestParam(required = false) String status,
            @RequestParam String role,
            @RequestParam String createdByEmail,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.filterPartners(name, email, businessEmail, instituteType,
                contractType, conductedBy, status, role, createdByEmail, page, size);
    }

    @GetMapping("/status-countPartner")
    public ResponseEntity<Map<String, Long>> getAllStatusWiseCount() {
        Map<String, Long> response = service.getAllStatusWiseCount();
        return ResponseEntity.ok(response);
    }

}