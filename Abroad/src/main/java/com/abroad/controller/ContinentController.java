package com.abroad.controller;

import com.abroad.entity.Continent;
import com.abroad.service.ContinentService;
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
public class ContinentController {
    @Autowired
    private ContinentService service;

    @PostMapping("/createContinent")
    public ResponseEntity<Continent> createContinent(@RequestPart("continent") String continentJson,
                                                     @RequestParam(value = "image", required = false) MultipartFile image,
                                                     @RequestParam String role,
                                                     @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Continent continent = mapper.readValue(continentJson, Continent.class);
        return ResponseEntity.ok(service.createContinent(continent, image, role, email));
    }

    @PutMapping("/updateContinent/{id}")
    public ResponseEntity<Continent> updateContinent(@PathVariable Long id,
                                                     @RequestPart("continent") String continentJson,
                                                     @RequestParam(value = "image", required = false) MultipartFile image,
                                                     @RequestParam String role,
                                                     @RequestParam String email) throws JsonProcessingException {
        Continent continent = new ObjectMapper().readValue(continentJson, Continent.class);
        return ResponseEntity.ok(service.updateContinent(id, continent, image, role, email));
    }

    @GetMapping("/getAllContinents")
    public ResponseEntity<List<Continent>> getAllContinents(@RequestParam String role,
                                                            @RequestParam String email) {
        return ResponseEntity.ok(service.getAllContinents(role, email));
    }

    @GetMapping("/getContinentById/{id}")
    public ResponseEntity<Continent> getContinentById(@PathVariable Long id,
                                                      @RequestParam String role,
                                                      @RequestParam String email) {
        return ResponseEntity.ok(service.getContinentById(id, role, email));
    }

    @DeleteMapping("/deleteContinent/{id}")
    public ResponseEntity<String> deleteContinent(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email) {
        service.deleteContinent(id, role, email);
        return ResponseEntity.ok("Continent deleted successfully");
    }
}
