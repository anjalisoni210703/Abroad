package com.abroad.controller;

import com.abroad.entity.College;
import com.abroad.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CollegeController {
    @Autowired
    private CollegeService collegeService;

    @PostMapping
    public College createCollege(@RequestBody College college) {
        return collegeService.saveCollege(college);
    }

    @GetMapping
    public List<College> getAllColleges() {
        return collegeService.getAllColleges();
    }

    @GetMapping("/university/{universityId}")
    public List<College> getCollegesByUniversity(@PathVariable Long universityId) {
        return collegeService.getCollegesByUniversityId(universityId);
    }
    @PutMapping("/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable Long id, @RequestBody College college) {
        College updated = collegeService.updateCollege(id, college);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

}
