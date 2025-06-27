package com.abroad.Service;

import com.abroad.Entity.AbroadCourse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    AbroadCourse createCourse(AbroadCourse abroadCourse, MultipartFile image, String role, String email, Long streamId);
    List<AbroadCourse> getAllCourses(String role, String email, String branchCode, Long streamId);
    AbroadCourse getCourseById(Long id, String role, String email);
    AbroadCourse updateCourse(Long id, AbroadCourse abroadCourse, MultipartFile image, String role, String email);
    void deleteCourse(Long id, String role, String email);
}
