package com.abroad.Controller;

import com.abroad.Entity.AbroadCourse;
import com.abroad.Service.CourseService;
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
@CrossOrigin(origins = "https://wayabroad.in")
public class CourseController {
    @Autowired
    private CourseService service;

    @PostMapping("/createCourse")
    public ResponseEntity<AbroadCourse> createCourse(@RequestPart("course") String courseJson,
                                                     @RequestParam String role,
                                                     @RequestParam String email,
                                                     @RequestParam Long streamId) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadCourse abroadCourse = mapper.readValue(courseJson, AbroadCourse.class);
        return ResponseEntity.ok(service.createCourse(abroadCourse, role, email, streamId));
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<AbroadCourse> updateCourse(@PathVariable Long id,
                                                     @RequestPart("course") String courseJson,
                                                     @RequestParam String role,
                                                     @RequestParam String email) throws JsonProcessingException {

        AbroadCourse abroadCourse = new ObjectMapper().readValue(courseJson, AbroadCourse.class);
        return ResponseEntity.ok(service.updateCourse(id, abroadCourse, role, email));
    }

    @GetMapping("/getAllCourses")
    public ResponseEntity<List<AbroadCourse>> getAllCourses(@RequestParam String role,
                                                            @RequestParam String email,
                                                            @RequestParam(required = false) Long streamId) {
        return ResponseEntity.ok(service.getAllCourses(role, email, streamId));
    }

    @GetMapping("/getCourseById/{id}")
    public ResponseEntity<AbroadCourse> getCourseById(@PathVariable Long id,
                                                      @RequestParam String role,
                                                      @RequestParam String email) {
        return ResponseEntity.ok(service.getCourseById(id, role, email));
    }

    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id,
                                               @RequestParam String role,
                                               @RequestParam String email) {
        service.deleteCourse(id, role, email);
        return ResponseEntity.ok("Course deleted successfully");
    }
}
