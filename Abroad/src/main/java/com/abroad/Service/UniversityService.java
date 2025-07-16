package com.abroad.Service;

import com.abroad.Entity.AbroadUniversity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UniversityService {
    AbroadUniversity createUniversity(AbroadUniversity abroadUniversity, MultipartFile image, String role, String email, Long cityId);
    List<AbroadUniversity> getAllUniversities(String role, String email, Long cityId);

    AbroadUniversity getUniversityById(Long id, String role, String email);
    AbroadUniversity updateUniversity(Long id, AbroadUniversity abroadUniversity, MultipartFile image, String role, String email);
    void deleteUniversity(Long id, String role, String email);
}
