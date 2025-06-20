package com.abroad.controller;

import com.abroad.entity.AboutUs;
import com.abroad.service.AboutUsService;
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
    public ResponseEntity<AboutUs> createAboutUs(@RequestPart("aboutUs") String aboutUsJson,
                                                 @RequestParam("image") MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AboutUs aboutUs = mapper.readValue(aboutUsJson, AboutUs.class);
        return ResponseEntity.ok(service.createAboutUs(aboutUs, image, role, email));
    }

    @PutMapping("/updateAboutUs/{id}")
    public ResponseEntity<AboutUs> updateAboutUs(@PathVariable int id,
                                                 @RequestPart("aboutUs") String AboutUSJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email)  throws JsonProcessingException {
        AboutUs aboutUs = new ObjectMapper().readValue(AboutUSJson, AboutUs.class);
        return ResponseEntity.ok(service.updateAboutUs(id, aboutUs, image, role, email));
    }

    @GetMapping("/getAllAboutUs")
    public ResponseEntity<List<AboutUs>> getAllAboutUs(@RequestParam String role,
                                                       @RequestParam String email) {
        return ResponseEntity.ok(service.getAllAboutUs(role, email));
    }

    @GetMapping("/getAboutUsById/{id}")
    public ResponseEntity<AboutUs> getAboutUsById(@PathVariable int id,
                                                  @RequestParam String role,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getAboutUsById(id, role, email));
    }

    @DeleteMapping("/deleteAboutUs/{id}")
    public ResponseEntity<String> deleteAboutUs(@PathVariable int id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deleteAboutUs(id, role, email);
        return ResponseEntity.ok("AboutUs deleted successfully");
    }
}