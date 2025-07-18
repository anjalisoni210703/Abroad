package com.abroad.Controller;

import com.abroad.Entity.AbroadExamPreparation;
import com.abroad.Service.AbroadExamPreparationService;
import com.abroad.Serviceimpl.AbroadExamPreparationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadExamPreparationController {

    @Autowired
    private AbroadExamPreparationServiceImpl service;

    @PostMapping("/createExamPreparation")
    public ResponseEntity<AbroadExamPreparation> createExam(@RequestBody AbroadExamPreparation exam) {
        return ResponseEntity.ok(service.createExam(exam));
    }

    @GetMapping("/getAllExamsPreparation")
    public ResponseEntity<List<AbroadExamPreparation>> getAllExams() {
        return ResponseEntity.ok(service.getAllExams());
    }

    @GetMapping("/getExamPreparationById/{id}")
    public ResponseEntity<AbroadExamPreparation> getExamById(@PathVariable Long id,
                                                             @RequestParam String role,
                                                             @RequestParam String email) {
        return ResponseEntity.ok(service.getExamById(id, role, email));
    }

    @PutMapping("/updateExamPreparation/{id}")
    public ResponseEntity<AbroadExamPreparation> updateExam(@PathVariable Long id,
                                                            @RequestBody AbroadExamPreparation exam,
                                                            @RequestParam String role,
                                                            @RequestParam String email) {
        return ResponseEntity.ok(service.updateExam(id, exam, role, email));
    }

    @DeleteMapping("/deleteExamPreparation/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable Long id,
                                             @RequestParam String role,
                                             @RequestParam String email) {
        service.deleteExam(id, role, email);
        return ResponseEntity.ok("Exam deleted successfully");
    }
}
