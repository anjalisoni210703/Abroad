package com.abroad.service;

import com.abroad.entity.AboutUs;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AboutUsService {
    AboutUs createAboutUs(AboutUs aboutUs, MultipartFile image, String role, String email);
    List<AboutUs> getAllAboutUs(String role, String email);
    AboutUs getAboutUsById(int id, String role, String email);
    AboutUs updateAboutUs(int id,AboutUs aboutUs, MultipartFile image, String role, String email);
    void deleteAboutUs(int id, String role, String email);
}