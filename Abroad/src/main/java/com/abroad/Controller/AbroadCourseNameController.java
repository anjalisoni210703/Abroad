package com.abroad.Controller;

import com.abroad.Entity.AbroadCourseName;
import com.abroad.Service.AbroadCourseNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AbroadCourseNameController {

    @Autowired
    private AbroadCourseNameService courseNameService;

    @PostMapping("createCourseName")
    public ResponseEntity<AbroadCourseName> createCourseName(@RequestParam String role,
                                                             @RequestParam String email,
                                                             @RequestBody AbroadCourseName courseName){
        return ResponseEntity.ok(courseNameService.createCourseName(role, email, courseName));
    }

    @GetMapping("getCourseNameById/{id}")
    public ResponseEntity<AbroadCourseName> getById(@PathVariable Long id,
                                                    @RequestParam String role,
                                                    @RequestParam String email){
        return ResponseEntity.ok(courseNameService.getCourseNameById(id, role, email));
    }

    @GetMapping("getAllCourseName")
    public ResponseEntity<?> getAll(@RequestParam String role,
                                    @RequestParam String email){
        return ResponseEntity.ok(courseNameService.getAllCourseName(role, email));
    }

    @PutMapping("updateCourseName/{id}")
    public ResponseEntity<AbroadCourseName> update(@PathVariable Long id,
                                                   @RequestParam String role,
                                                   @RequestParam String email,
                                                   @RequestBody AbroadCourseName courseName){
        return ResponseEntity.ok(courseNameService.updateCourseName(id, role, email, courseName));
    }

    @DeleteMapping("deleteCourseName/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                       @RequestParam String role,
                       @RequestParam String email){
        return ResponseEntity.ok(courseNameService.deleteCourseName(id, role, email));
    }
}
