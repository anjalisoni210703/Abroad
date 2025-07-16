package com.abroad.Controller;

import com.abroad.Entity.AbroadState;
import com.abroad.Service.StateService;
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
public class StateController {

    @Autowired
    private StateService service;

    @PostMapping("/createState")
    public ResponseEntity<AbroadState> createState(@RequestPart("state") String stateJson,
                                                   @RequestParam("image") MultipartFile image,
                                                   @RequestParam("countryId") Long countryId,
                                                   @RequestParam String role,
                                                   @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        AbroadState state = mapper.readValue(stateJson, AbroadState.class);
        return ResponseEntity.ok(service.createState(state, image, countryId, role, email));
    }

    @GetMapping("/getAllStates")
    public ResponseEntity<List<AbroadState>> getAllStates(@RequestParam String role,
                                                          @RequestParam String email,
                                                          @RequestParam(required = false) Long countryId) {
        return ResponseEntity.ok(service.getAllStates(role, email, countryId));
    }

    @GetMapping("/getStateById/{id}")
    public ResponseEntity<AbroadState> getStateById(@PathVariable Long id,
                                                    @RequestParam String role,
                                                    @RequestParam String email) {
        return ResponseEntity.ok(service.getStateById(id, role, email));
    }

    @PutMapping("/updateState/{id}")
    public ResponseEntity<AbroadState> updateState(@PathVariable Long id,
                                                   @RequestPart("state") String stateJson,
                                                   @RequestParam(value = "image", required = false) MultipartFile image,
                                                   @RequestParam(value = "countryId", required = false) Long countryId,
                                                   @RequestParam String role,
                                                   @RequestParam String email) throws JsonProcessingException {
        AbroadState state = new ObjectMapper().readValue(stateJson, AbroadState.class);
        return ResponseEntity.ok(service.updateState(id, state, image, countryId, role, email));
    }

    @DeleteMapping("/deleteState/{id}")
    public ResponseEntity<String> deleteState(@PathVariable Long id,
                                              @RequestParam String role,
                                              @RequestParam String email) {
        service.deleteState(id, role, email);
        return ResponseEntity.ok("State deleted successfully");
    }
}