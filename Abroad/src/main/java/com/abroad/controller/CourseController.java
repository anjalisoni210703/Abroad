package com.abroad.controller;

import com.abroad.entity.Course;
import com.abroad.repository.CourseRepository;
import com.abroad.service.CourseService;
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
@CrossOrigin(origins = "https://pjsofttech.in")
public class CourseController {
    @Autowired
    private CourseService service;

    @PostMapping("/createCourse")
    public ResponseEntity<Course> createCourse(@RequestPart("course") String courseJson,
                                               @RequestParam(value = "image", required = false) MultipartFile image,
                                               @RequestParam String role,
                                               @RequestParam String email) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Course course = mapper.readValue(courseJson, Course.class);
        return ResponseEntity.ok(service.createCourse(course, image, role, email));
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,
                                               @RequestPart("course") String courseJson,
                                               @RequestParam(value = "image", required = false) MultipartFile image,
                                               @RequestParam String role,
                                               @RequestParam String email) throws JsonProcessingException {

        Course course = new ObjectMapper().readValue(courseJson, Course.class);
        return ResponseEntity.ok(service.updateCourse(id, course, image, role, email));
    }

    @GetMapping("/getAllCourses")
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam String role,
                                                      @RequestParam String email) {
        return ResponseEntity.ok(service.getAllCourses(role, email));
    }

    @GetMapping("/getCourseById/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id,
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
