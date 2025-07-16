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
    public ResponseEntity<AbroadCourse> createCourseWithImage(@RequestPart("course") String courseJson,
                                                              @RequestParam(value = "image",required = false) MultipartFile image,
                                                              @RequestParam String role,
                                                              @RequestParam String email,
                                                              @RequestParam Long streamId) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadCourse abroadCourse = mapper.readValue(courseJson, AbroadCourse.class);
        return ResponseEntity.ok(service.createCourse(abroadCourse, image, role, email, streamId));
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<AbroadCourse> updateCourseWithImage(@PathVariable Long id,
                                                              @RequestPart("course") String courseJson,
                                                              @RequestParam(value = "image", required = false) MultipartFile image,
                                                              @RequestParam String role,
                                                              @RequestParam String email) throws JsonProcessingException {
        AbroadCourse abroadCourse = new ObjectMapper().readValue(courseJson, AbroadCourse.class);
        return ResponseEntity.ok(service.updateCourse(id, abroadCourse, image, role, email));
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

    @GetMapping("/filterCourses")
    public ResponseEntity<List<AbroadCourse>> filterCourses(
            @RequestParam(required = false) List<Long> streamIds,
            @RequestParam(required = false) List<Long> collegeIds,
            @RequestParam(required = false) List<Long> universityIds,
            @RequestParam(required = false) List<Long> cityIds,
            @RequestParam(required = false) List<Long> stateIds,
            @RequestParam(required = false) List<Long> countryIds,
            @RequestParam(required = false) List<Long> continentIds,
            @RequestParam String role,
            @RequestParam String email) {

        List<AbroadCourse> result = service.filterCourses(
                streamIds,
                collegeIds,
                universityIds,
                cityIds,
                stateIds,
                countryIds,
                continentIds,
                role,
                email
        );

        return ResponseEntity.ok(result);
    }

}
