package com.abroad.controller;

import com.abroad.entity.University;
import com.abroad.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UniversityController {
    @Autowired
    private UniversityService universityService;

    @PostMapping
    public University createUniversity(@RequestBody University university) {
        return universityService.saveUniversity(university);
    }

    @GetMapping
    public List<University> getAllUniversities() {
        return universityService.getAllUniversities();
    }

    @GetMapping("/{id}")
    public University getUniversity(@PathVariable Long id) {
        return universityService.getUniversityById(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable Long id, @RequestBody University university) {
        University updated = universityService.updateUniversity(id, university);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

}
