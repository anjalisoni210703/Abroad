package com.abroad.Service;

import com.abroad.Entity.AbroadEnquiry;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EnquiryService {

    AbroadEnquiry createEnquiry(AbroadEnquiry abroadEnquiry, MultipartFile image, MultipartFile document1, MultipartFile document2,
                                String role, String email, Long continentId, Long countryId, Long universityId,
                                Long stateId, Long cityId, Long collegeId);

    List<AbroadEnquiry> getAllEnquiries(String role, String email);

    AbroadEnquiry getEnquiryById(Long id, String role, String email);

    AbroadEnquiry updateEnquiry(Long id, AbroadEnquiry abroadEnquiry, MultipartFile image, MultipartFile document1, MultipartFile document2,
                                String role, String email);

    void deleteEnquiry(Long id, String role, String email);

    Page<AbroadEnquiry> filterEnquiries(
            String continent, String country, String stream, String course, String status,
            String branchCode, String role, String email, String fullName,
            String enquiryDateFilter, LocalDate startDate, LocalDate endDate,
            String applyFor,
            String conductBy,
            int page, int size
    );

    List<Map<String, Object>> getInquiryCountByCourseAsMap(String branchCode);

    List<Map<String, Object>> getInquiryCountByStreamAsMap(String branchCode);

    List<Map<String, Object>> getInquiryCountByConductByAsMap(String branchCode);

    List<Map<String, Object>> getInquiryCountByStreamForMonth(int month, String branchCode);

    List<Map<String, Object>> getInquiryCountByCourseForMonth(int month, String branchCode);

    List<Map<String, Object>> getInquiryCountByConductByForMonth(int month, String branchCode);

    Map<String, Long> getAllEnquiryCounts(String branchCode);

    Map<String, Long> getAllStatusWiseCount(String branchCode);

    List<AbroadEnquiry> getAllEnquiryDataByIdOrNameOrEmailOrPhone(Long id, String name, String email, Long phoneNo);

    Map<String, Object> getDailyInquiryCountsWithTotal(int year, int month);
    List<Map<String, Object>> getInquiryCountByStreamForYearRange(int startYear, int endYear, String branchCode);
    List<Map<String, Object>> getInquiryCountByCourseForYearRange(int startYear, int endYear, String branchCode);
    Map<String, Object> getMonthlyInquiryCountsWithTotal(int startYear, int endYear);
    Map<String, Object> getYearlyInquiryCounts();

    List<AbroadEnquiry> getAllEnquiryByBranchCode(String role, String email, String branchCode);
    List<AbroadEnquiry> getAllEnquiryByCreatedByEmail(String role, String email, String createdByEmail);


}