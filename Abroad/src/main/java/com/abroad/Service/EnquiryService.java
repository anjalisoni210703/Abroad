package com.abroad.Service;

import com.abroad.Entity.AbroadEnquiry;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EnquiryService {
    AbroadEnquiry createEnquiry(AbroadEnquiry abroadEnquiry, MultipartFile image, String role, String email);
    List<AbroadEnquiry> getAllEnquiries(String role, String email);
    AbroadEnquiry getEnquiryById(Long id, String role, String email);
    AbroadEnquiry updateEnquiry(Long id, AbroadEnquiry abroadEnquiry, MultipartFile image, String role, String email);
    void deleteEnquiry(Long id, String role, String email);
}
