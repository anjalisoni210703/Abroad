package com.abroad.Controller;

import com.abroad.Entity.AbroadExam;
import com.abroad.Serviceimpl.AbroadExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadExamController {

    @Autowired
    private AbroadExamServiceImpl abroadExamService;

    @PostMapping("/addExam")
    public ResponseEntity<AbroadExam> addExam(@RequestParam String role,
                                              @RequestParam String email,
                                              @RequestBody AbroadExam exam){
        return ResponseEntity.ok(abroadExamService.addExam(role, email, exam));
    }

    @GetMapping("/getExamById/{id}")
    public ResponseEntity<AbroadExam> getById(@PathVariable Long id){
        return ResponseEntity.ok(abroadExamService.getById(id));
    }

    @GetMapping("/getAllExam")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(abroadExamService.getAll());
    }

    @PutMapping("/updateExams/{id}")
    public ResponseEntity<AbroadExam> update(@RequestParam String role,
                                              @RequestParam String email,
                                              @PathVariable Long id,
                                              @RequestBody AbroadExam uexam){
        return ResponseEntity.ok(abroadExamService.updateExam(id,role, email,uexam));
    }

    @DeleteMapping("/deleteExam/{id}")
    public ResponseEntity<?> delete(@RequestParam String role,
                                    @RequestParam String email,
                                    @PathVariable Long id){
        return ResponseEntity.ok(abroadExamService.deleteExam(id,role, email));
    }
}
