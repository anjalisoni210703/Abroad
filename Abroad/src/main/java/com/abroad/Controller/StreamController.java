package com.abroad.Controller;

import com.abroad.Entity.AbroadStream;
import com.abroad.Service.StreamService;
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
public class StreamController {
    @Autowired
    private StreamService service;

    @PostMapping("/createStream")
    public ResponseEntity<AbroadStream> createStream(@RequestPart("stream") String streamJson,
                                                     @RequestParam(value = "image", required = false) MultipartFile image,
                                                     @RequestParam String role,
                                                     @RequestParam String email,
                                                     @RequestParam Long collegeId) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadStream abroadStream = mapper.readValue(streamJson, AbroadStream.class);
        return ResponseEntity.ok(service.createStream(abroadStream, image, role, email, collegeId));
    }

    @PutMapping("/updateStream/{id}")
    public ResponseEntity<AbroadStream> updateStream(@PathVariable Long id,
                                                     @RequestPart("stream") String streamJson,
                                                     @RequestParam(value = "image", required = false) MultipartFile image,
                                                     @RequestParam String role,
                                                     @RequestParam String email) throws JsonProcessingException {
        AbroadStream abroadStream = new ObjectMapper().readValue(streamJson, AbroadStream.class);
        return ResponseEntity.ok(service.updateStream(id, abroadStream, image, role, email));
    }

    @GetMapping("/getAllStreams")
    public ResponseEntity<List<AbroadStream>> getAllStreams(@RequestParam String role,
                                                            @RequestParam String email,
                                                            @RequestParam(required = false) Long collegeId) {
        return ResponseEntity.ok(service.getAllStreams(role, email, collegeId));
    }


    @GetMapping("/getStreamById/{id}")
    public ResponseEntity<AbroadStream> getStreamById(@PathVariable Long id,
                                                      @RequestParam String role,
                                                      @RequestParam String email) {
        return ResponseEntity.ok(service.getStreamById(id, role, email));
    }

    @DeleteMapping("/deleteStream/{id}")
    public ResponseEntity<String> deleteStream(@PathVariable Long id,
                                               @RequestParam String role,
                                               @RequestParam String email) {
        service.deleteStream(id, role, email);
        return ResponseEntity.ok("Stream deleted successfully");
    }
}
