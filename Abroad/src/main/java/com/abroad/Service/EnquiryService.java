package com.abroad.Service;

import com.abroad.Entity.AbroadEnquiry;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface EnquiryService {
    AbroadEnquiry createEnquiry(AbroadEnquiry abroadEnquiry, MultipartFile image, String role, String email,
                                Long continentId, Long countryId, Long universityId, Long streamId, Long courseId);
    List<AbroadEnquiry> getAllEnquiries(String role, String email);
    AbroadEnquiry getEnquiryById(Long id, String role, String email);
    AbroadEnquiry updateEnquiry(Long id, AbroadEnquiry abroadEnquiry, MultipartFile image, String role, String email);
    void deleteEnquiry(Long id, String role, String email);
    Page<AbroadEnquiry> filterEnquiries(String continent, String country, String stream, String course, String status,
                                        String branchCode, String role, String email, String fullName,
                                        String enquiryDateFilter, LocalDate startDate, LocalDate endDate,
                                        int page, int size);
}
