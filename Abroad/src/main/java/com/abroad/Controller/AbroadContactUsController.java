package com.abroad.Controller;

import com.abroad.Entity.AbroadContactUs;
import com.abroad.Service.AbroadContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AbroadContactUsController {

    @Autowired
    private AbroadContactUsService service;

    @PostMapping("/createContactUS")
    public ResponseEntity<AbroadContactUs> create(@RequestBody AbroadContactUs contactus){
        return ResponseEntity.ok(service.createContactUs(contactus));
    }

    @GetMapping("/getContactUsById/{id}")
    public ResponseEntity<AbroadContactUs> getById(@PathVariable Long id,
                                                   @RequestParam String role,String email){
        return ResponseEntity.ok(service.getContactUsById(id,role,email));
    }

    @GetMapping("/getAllContactUs")
    public ResponseEntity<?> getAll( @RequestParam String role,String email){
        return ResponseEntity.ok(service.getAllContactUs(role,email));
    }

    @PutMapping("/updateContactUS/{id}")
    public ResponseEntity<AbroadContactUs> update(@PathVariable Long id,
                                                  @RequestBody AbroadContactUs contactUs,
                                                  @RequestParam String role,String email){
        return ResponseEntity.ok(service.update(id, contactUs,role,email));
    }

    @DeleteMapping("/deleteContactUs/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @RequestParam String role,String email){
        return ResponseEntity.ok(service.delete(id,role,email));
    }
}
