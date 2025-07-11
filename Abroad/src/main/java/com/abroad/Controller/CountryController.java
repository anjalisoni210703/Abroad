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
@CrossOrigin(origins = "https://wayabroad.in")
public class CountryController {

    @Autowired
    private CountryService service;

    @PostMapping("/createCountry")
    public ResponseEntity<AbroadCountry> createCountryWithImage(@RequestPart("country") String countryJson,
                                                                @RequestParam("image") MultipartFile image,
                                                                @RequestParam("continentId") Long continentId,
                                                                @RequestParam String role,
                                                                @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadCountry country = mapper.readValue(countryJson, AbroadCountry.class);
        return ResponseEntity.ok(service.createCountry(country, image, continentId, role, email));
    }

    @GetMapping("/getAllCountries")
    public ResponseEntity<List<AbroadCountry>> getAllCountries(@RequestParam String role,
                                                               @RequestParam String email,
                                                               @RequestParam(required = false) Long continentId) {
        return ResponseEntity.ok(service.getAllCountries(role, email, continentId));
    }

    @GetMapping("/getCountryById/{id}")
    public ResponseEntity<AbroadCountry> getCountryById(@PathVariable Long id,
                                                        @RequestParam String role,
                                                        @RequestParam String email) {
        return ResponseEntity.ok(service.getCountryById(id, role, email));
    }

    @PutMapping("/updateCountry/{id}")
    public ResponseEntity<AbroadCountry> updateCountryWithImage(@PathVariable Long id,
                                                                @RequestPart("country") String countryJson,
                                                                @RequestParam(value = "image", required = false) MultipartFile image,
                                                                @RequestParam(value = "continentId", required = false) Long continentId,
                                                                @RequestParam String role,
                                                                @RequestParam String email) throws JsonProcessingException {
        AbroadCountry country = new ObjectMapper().readValue(countryJson, AbroadCountry.class);
        return ResponseEntity.ok(service.updateCountry(id, country, image, continentId, role, email));
    }

    @DeleteMapping("/deleteCountry/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deleteCountry(id, role, email);
        return ResponseEntity.ok("Country deleted successfully");
    }
}