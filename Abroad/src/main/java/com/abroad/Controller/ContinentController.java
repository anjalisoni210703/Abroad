package com.abroad.Controller;

import com.abroad.Entity.AbroadContinent;
import com.abroad.Service.ContinentService;
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
@CrossOrigin(origins = "https://wayabroad.in")
public class ContinentController {
    @Autowired
    private ContinentService service;

    @PostMapping("/createContinent")
    public ResponseEntity<AbroadContinent> createContinent(@RequestPart("continent") String continentJson,
                                                           @RequestParam String role,
                                                           @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadContinent abroadContinent = mapper.readValue(continentJson, AbroadContinent.class);
        return ResponseEntity.ok(service.createContinent(abroadContinent, role, email));
    }

    @PutMapping("/updateContinent/{id}")
    public ResponseEntity<AbroadContinent> updateContinent(@PathVariable Long id,
                                                           @RequestPart("continent") String continentJson,
                                                           @RequestParam String role,
                                                           @RequestParam String email) throws JsonProcessingException {
        AbroadContinent abroadContinent = new ObjectMapper().readValue(continentJson, AbroadContinent.class);
        return ResponseEntity.ok(service.updateContinent(id, abroadContinent, role, email));
    }

    @GetMapping("/getAllContinents")
    public ResponseEntity<List<AbroadContinent>> getAllContinents(@RequestParam String role,
                                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getAllContinents(role, email));
    }

    @GetMapping("/getContinentById/{id}")
    public ResponseEntity<AbroadContinent> getContinentById(@PathVariable Long id,
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
