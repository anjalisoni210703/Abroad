package com.abroad.Service;

import com.abroad.Entity.AbroadAboutUs;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AboutUsService {
    AbroadAboutUs createAboutUs(AbroadAboutUs abroadAboutUs, MultipartFile image, String role, String email);
    List<AbroadAboutUs> getAllAboutUs(String role, String email, String branchCode);
    AbroadAboutUs getAboutUsById(int id, String role, String email, String branchCode);
    AbroadAboutUs updateAboutUs(int id, AbroadAboutUs abroadAboutUs, MultipartFile image, String role, String email);
    void deleteAboutUs(int id, String role, String email);
}