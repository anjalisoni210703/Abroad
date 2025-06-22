package com.abroad.service;

import com.abroad.entity.University;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UniversityService {
    University createUniversity(University university, MultipartFile image, String role, String email);
    List<University> getAllUniversities(String role, String email);
    University getUniversityById(Long id, String role, String email);
    University updateUniversity(Long id, University university, MultipartFile image, String role, String email);
    void deleteUniversity(Long id, String role, String email);
}
