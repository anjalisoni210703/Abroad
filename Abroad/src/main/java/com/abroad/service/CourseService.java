package com.abroad.service;

import com.abroad.entity.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    Course createCourse(Course course, MultipartFile image, String role, String email);
    List<Course> getAllCourses(String role, String email);
    Course getCourseById(Long id, String role, String email);
    Course updateCourse(Long id, Course course, MultipartFile image, String role, String email);
    void deleteCourse(Long id, String role, String email);
}
