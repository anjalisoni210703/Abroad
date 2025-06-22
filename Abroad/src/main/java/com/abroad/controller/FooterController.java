package com.abroad.controller;

import com.abroad.entity.Footer;
import com.abroad.service.FooterService;
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
public class FooterController {
    @Autowired
    private FooterService service;

    @PostMapping("/createFooter")
    public ResponseEntity<Footer> createFooter(@RequestPart("footer") String footerJson,
                                               @RequestParam(value = "image", required = false) MultipartFile image,
                                               @RequestParam String role,
                                               @RequestParam String email) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Footer footer = mapper.readValue(footerJson, Footer.class);
        return ResponseEntity.ok(service.createFooter(footer, image, role, email));
    }

    @PutMapping("/updateFooter/{id}")
    public ResponseEntity<Footer> updateFooter(@PathVariable Long id,
                                               @RequestPart("footer") String footerJson,
                                               @RequestParam(value = "image", required = false) MultipartFile image,
                                               @RequestParam String role,
                                               @RequestParam String email) throws JsonProcessingException {
        Footer footer = new ObjectMapper().readValue(footerJson, Footer.class);
        return ResponseEntity.ok(service.updateFooter(id, footer, image, role, email));
    }

    @GetMapping("/getAllFooters")
    public ResponseEntity<List<Footer>> getAllFooters(@RequestParam String role,
                                                      @RequestParam String email) {
        return ResponseEntity.ok(service.getAllFooters(role, email));
    }

    @GetMapping("/getFooterById/{id}")
    public ResponseEntity<Footer> getFooterById(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        return ResponseEntity.ok(service.getFooterById(id, role, email));
    }

    @DeleteMapping("/deleteFooter/{id}")
    public ResponseEntity<String> deleteFooter(@PathVariable Long id,
                                               @RequestParam String role,
                                               @RequestParam String email) {
        service.deleteFooter(id, role, email);
        return ResponseEntity.ok("Footer deleted successfully");
    }
}
