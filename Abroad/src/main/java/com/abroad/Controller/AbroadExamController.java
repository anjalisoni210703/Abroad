package com.abroad.Controller;

import com.abroad.Entity.AbroadExam;
import com.abroad.Service.AbroadExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadExamController {

    @Autowired
    private AbroadExamService service;

    @PostMapping("/createExam")
    public ResponseEntity<AbroadExam> createExam(@RequestBody AbroadExam exam,
                                                 @RequestParam String role,
                                                 @RequestParam String email) {
        return ResponseEntity.ok(service.createExam(exam, role, email));
    }

    @GetMapping("/getAllExams")
    public ResponseEntity<List<AbroadExam>> getAllExams() {
        return ResponseEntity.ok(service.getAllExams());
    }

    @GetMapping("/getExamById/{id}")
    public ResponseEntity<AbroadExam> getExamById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getExamById(id, role, email));
    }

    @PutMapping("/updateExam/{id}")
    public ResponseEntity<AbroadExam> updateExam(@PathVariable Long id,
                                                 @RequestBody AbroadExam exam,
                                                 @RequestParam String role,
                                                 @RequestParam String email) {
        return ResponseEntity.ok(service.updateExam(id, exam, role, email));
    }

    @DeleteMapping("/deleteExam/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable Long id,
                                             @RequestParam String role,
                                             @RequestParam String email) {
        service.deleteExam(id, role, email);
        return ResponseEntity.ok("Exam deleted successfully");
    }
}
