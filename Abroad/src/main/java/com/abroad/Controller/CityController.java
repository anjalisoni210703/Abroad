package com.abroad.Controller;

import com.abroad.Entity.AbroadCity;
import com.abroad.Service.CityService;
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
public class CityController {

    @Autowired
    private CityService service;

    @PostMapping("/createCity")
    public ResponseEntity<AbroadCity> createCity(@RequestPart("city") String cityJson,
                                                 @RequestParam("image") MultipartFile image,
                                                 @RequestParam("stateId") Long stateId,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadCity city = mapper.readValue(cityJson, AbroadCity.class);
        return ResponseEntity.ok(service.createCity(city, image, stateId, role, email));
    }

    @GetMapping("/getAllCities")
    public ResponseEntity<List<AbroadCity>> getAllCities(@RequestParam String role,
                                                         @RequestParam String email,
                                                         @RequestParam(required = false) Long stateId) {
        return ResponseEntity.ok(service.getAllCities(role, email, stateId));
    }

    @GetMapping("/getCityById/{id}")
    public ResponseEntity<AbroadCity> getCityById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getCityById(id, role, email));
    }

    @PutMapping("/updateCity/{id}")
    public ResponseEntity<AbroadCity> updateCity(@PathVariable Long id,
                                                 @RequestPart("city") String cityJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam(value = "stateId", required = false) Long stateId,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        AbroadCity city = new ObjectMapper().readValue(cityJson, AbroadCity.class);
        return ResponseEntity.ok(service.updateCity(id, city, image, stateId, role, email));
    }

    @DeleteMapping("/deleteCity/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id,
                                             @RequestParam String role,
                                             @RequestParam String email) {
        service.deleteCity(id, role, email);
        return ResponseEntity.ok("City deleted successfully");
    }
}