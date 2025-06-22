package com.abroad.service;

import com.abroad.entity.College;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CollegeService {
    College createCollege(College college, MultipartFile image, String role, String email);
    List<College> getAllColleges(String role, String email);
    College getCollegeById(Long id, String role, String email);
    College updateCollege(Long id, College college, MultipartFile image, String role, String email);
    void deleteCollege(Long id, String role, String email);
}
