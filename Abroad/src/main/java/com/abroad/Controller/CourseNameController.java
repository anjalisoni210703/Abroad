package com.abroad.Controller;

import com.abroad.Entity.CourseName;
import com.abroad.Service.CourseNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CourseNameController {

    @Autowired
    private CourseNameService courseNameService;

    @PostMapping("createCourseeName")
    public ResponseEntity<CourseName> createCourseName(@RequestParam String role,
                                                       @RequestParam String email,
                                                       @RequestBody CourseName courseName){
        return ResponseEntity.ok(courseNameService.createCollegeName(role, email, courseName));
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<CourseName> getById(@PathVariable Long id,
                                              @RequestParam String role,
                                              @RequestParam String email){
        return ResponseEntity.ok(courseNameService.getCollegeNameById(id, role, email));
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(@RequestParam String role,
                                    @RequestParam String email){
        return ResponseEntity.ok(courseNameService.getAll(role, email));
    }

    @PutMapping("updateCourseName/{id}")
    public ResponseEntity<CourseName> update(@PathVariable Long id,
                                             @RequestParam String role,
                                             @RequestParam String email,
                                             @RequestBody CourseName courseName){
        return ResponseEntity.ok(courseNameService.update(id, role, email, courseName));
    }

    @DeleteMapping("deleteCourseName/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id,
                       @RequestParam String role,
                       @RequestParam String email){
        return ResponseEntity.ok(courseNameService.delete(id, role, email));
    }
}
