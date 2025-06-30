package com.abroad.Controller;

import com.abroad.Entity.AbroadCountry;
import com.abroad.Service.CountryService;
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
public class CountryController {

    @Autowired
    private CountryService service;

    @PostMapping("/createCountry")
    public ResponseEntity<AbroadCountry> createCountry(@RequestPart("country") String countryJson,
                                                       @RequestParam("continentId") Long continentId,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadCountry country = mapper.readValue(countryJson, AbroadCountry.class);
        return ResponseEntity.ok(service.createCountry(country, continentId, image, role, email));
    }

    @GetMapping("/getAllCountries")
    public ResponseEntity<List<AbroadCountry>> getAllCountries(@RequestParam String role,
                                                               @RequestParam String email,
                                                               @RequestParam String branchCode,
                                                               @RequestParam(required = false) Long continentId) {
        return ResponseEntity.ok(service.getAllCountries(role, email, branchCode, continentId));
    }

    @GetMapping("/getCountryById/{id}")
    public ResponseEntity<AbroadCountry> getCountryById(@PathVariable Long id,
                                                        @RequestParam String role,
                                                        @RequestParam String email,
                                                        @RequestParam String branchCode) {
        return ResponseEntity.ok(service.getCountryById(id, role, email, branchCode));
    }

    @PutMapping("/updateCountry/{id}")
    public ResponseEntity<AbroadCountry> updateCountry(@PathVariable Long id,
                                                       @RequestPart("country") String countryJson,
                                                       @RequestParam(value = "continentId", required = false) Long continentId,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email,
                                                       @RequestParam String branchCode) throws JsonProcessingException {
        AbroadCountry country = new ObjectMapper().readValue(countryJson, AbroadCountry.class);
        return ResponseEntity.ok(service.updateCountry(id, country, continentId, image, role, email, branchCode));
    }

    @DeleteMapping("/deleteCountry/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email,
                                                @RequestParam String branchCode) {
        service.deleteCountry(id, role, email, branchCode);
        return ResponseEntity.ok("Country deleted successfully");
    }
}