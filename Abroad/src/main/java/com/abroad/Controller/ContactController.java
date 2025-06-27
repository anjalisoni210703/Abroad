package com.abroad.Controller;

import com.abroad.Entity.AbroadContact;
import com.abroad.Service.ContactService;
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
public class ContactController {
    @Autowired
    private ContactService service;

    @PostMapping("/createContact")
    public ResponseEntity<AbroadContact> createContact(@RequestPart("contact") String contactJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadContact abroadContact = mapper.readValue(contactJson, AbroadContact.class);
        return ResponseEntity.ok(service.createContact(abroadContact, image, role, email));
    }

    @PutMapping("/updateContact/{id}")
    public ResponseEntity<AbroadContact> updateContact(@PathVariable Long id,
                                                       @RequestPart("contact") String contactJson,
                                                       @RequestParam(value = "image", required = false) MultipartFile image,
                                                       @RequestParam String role,
                                                       @RequestParam String email) throws JsonProcessingException {
        AbroadContact abroadContact = new ObjectMapper().readValue(contactJson, AbroadContact.class);
        return ResponseEntity.ok(service.updateContact(id, abroadContact, image, role, email));
    }

    @GetMapping("/getAllContacts")
    public ResponseEntity<List<AbroadContact>> getAllContacts(@RequestParam String role,
                                                              @RequestParam String email) {
        return ResponseEntity.ok(service.getAllContacts(role, email));
    }

    @GetMapping("/getContactById/{id}")
    public ResponseEntity<AbroadContact> getContactById(@PathVariable Long id,
                                                        @RequestParam String role,
                                                        @RequestParam String email) {
        return ResponseEntity.ok(service.getContactById(id, role, email));
    }

    @DeleteMapping("/deleteContact/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id,
                                                @RequestParam String role,
                                                @RequestParam String email) {
        service.deleteContact(id, role, email);
        return ResponseEntity.ok("Contact deleted successfully");
    }

}
