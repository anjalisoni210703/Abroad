package com.abroad.controller;

import com.abroad.entity.AboutUs;
import com.abroad.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AboutUsController {

    @Autowired
    private AboutUsService aboutUsService;

    @PostMapping("/createAboutUs")
    public ResponseEntity<AboutUs> createAboutUs(@RequestBody AboutUs aboutUs) {
        return ResponseEntity.ok(aboutUsService.createAboutUs(aboutUs));
    }

    @GetMapping("/getAllAboutUs")
    public ResponseEntity<List<AboutUs>> getAllAboutUs() {
        return ResponseEntity.ok(aboutUsService.getAllAboutUs());
    }

    @GetMapping("/getAboutUsById/{id}")
    public ResponseEntity<AboutUs> getAboutUsById(@PathVariable int id) {
        return ResponseEntity.ok(aboutUsService.getAboutUsById(id));
    }

    @PutMapping("/updateAboutUs/{id}")
    public ResponseEntity<AboutUs> updateAboutUs(@PathVariable int id, @RequestBody AboutUs aboutUs) {
        return ResponseEntity.ok(aboutUsService.updateAboutUs(id, aboutUs));
    }

    @DeleteMapping("/deleteAboutUs/{id}")
    public ResponseEntity<String> deleteAboutUs(@PathVariable int id) {
        aboutUsService.deleteAboutUs(id);
        return ResponseEntity.ok("AboutUs entry deleted successfully.");
    }
}
