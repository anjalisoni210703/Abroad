package com.abroad.Service;

import com.abroad.Entity.AbroadCollege;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CollegeService {
    AbroadCollege createCollege(AbroadCollege abroadCollege, MultipartFile image, String role, String email, Long universityId) throws IOException;
    List<AbroadCollege> getAllColleges(String role, String email, Long universityId);
    AbroadCollege getCollegeById(Long id, String role, String email);
    AbroadCollege updateCollege(Long id, AbroadCollege abroadCollege, MultipartFile image, String role, String email);
    void deleteCollege(Long id, String role, String email);
}
