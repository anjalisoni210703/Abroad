package com.abroad.controller;

import com.abroad.entity.Address;
import com.abroad.service.AddressService;
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
public class AddressController {
    @Autowired
    private AddressService service;

    @PostMapping("/createAddress")
    public ResponseEntity<Address> createAddress(@RequestPart("address") String addressJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Address address = mapper.readValue(addressJson, Address.class);
        return ResponseEntity.ok(service.createAddress(address, image, role, email));
    }

    @PutMapping("/updateAddress/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id,
                                                 @RequestPart("address") String addressJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        Address address = new ObjectMapper().readValue(addressJson, Address.class);
        return ResponseEntity.ok(service.updateAddress(id, address, image, role, email));
    }

    @GetMapping("/getAllAddresses")
    public ResponseEntity<List<Address>> getAllAddresses(@RequestParam String role,
                                                         @RequestParam String email) {
        return ResponseEntity.ok(service.getAllAddresses(role, email));
    }

    @GetMapping("/getAddressById/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getAddressById(id, role, email));
    }

    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deleteAddress(id, role, email);
        return ResponseEntity.ok("Address deleted successfully");
    }
}
