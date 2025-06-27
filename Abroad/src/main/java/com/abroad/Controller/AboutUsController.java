package com.abroad.Controller;

import com.abroad.Entity.AbroadAboutUs;
import com.abroad.Service.AboutUsService;
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
public class AboutUsController {

    @Autowired
    private AboutUsService service;

    @PostMapping("/createAboutUs")
    public ResponseEntity<AbroadAboutUs> createAboutUs(@RequestPart("aboutUs") String aboutUsJson,
                                                       @RequestParam("image") MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadAboutUs abroadAboutUs = mapper.readValue(aboutUsJson, AbroadAboutUs.class);
        return ResponseEntity.ok(service.createAboutUs(abroadAboutUs, image, role, email));
    }

    @PutMapping("/updateAboutUs/{id}")
    public ResponseEntity<AbroadAboutUs> updateAboutUs(@PathVariable int id,
                                                       @RequestPart("aboutUs") String AboutUSJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email)  throws JsonProcessingException {
        AbroadAboutUs abroadAboutUs = new ObjectMapper().readValue(AboutUSJson, AbroadAboutUs.class);
        return ResponseEntity.ok(service.updateAboutUs(id, abroadAboutUs, image, role, email));
    }

    @GetMapping("/getAllAboutUs")
    public ResponseEntity<List<AbroadAboutUs>> getAllAboutUs(@RequestParam String role,
                                                             @RequestParam String email,
                                                             @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getAllAboutUs(role, email, branchCode));
    }

    @GetMapping("/getAboutUsById/{id}")
    public ResponseEntity<AbroadAboutUs> getAboutUsById(@PathVariable int id,
                                                        @RequestParam String role,
                                                        @RequestParam String email,
                                                        @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getAboutUsById(id, role, email, branchCode));
    }


    @DeleteMapping("/deleteAboutUs/{id}")
    public ResponseEntity<String> deleteAboutUs(@PathVariable int id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deleteAboutUs(id, role, email);
        return ResponseEntity.ok("AboutUs deleted successfully");
    }
}