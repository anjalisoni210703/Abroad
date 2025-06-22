package com.abroad.service;

import com.abroad.dto.EnquiryDTO;
import com.abroad.dto.EnquiryFilterDTO;
import com.abroad.entity.Enquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EnquiryService {
    Enquiry createEnquiry(Enquiry enquiry, MultipartFile image, String role, String email);
    List<Enquiry> getAllEnquiries(String role, String email);
    Enquiry getEnquiryById(Long id, String role, String email);
    Enquiry updateEnquiry(Long id, Enquiry enquiry, MultipartFile image, String role, String email);
    void deleteEnquiry(Long id, String role, String email);
}
