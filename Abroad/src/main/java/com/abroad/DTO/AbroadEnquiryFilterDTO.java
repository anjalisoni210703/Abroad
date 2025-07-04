package com.abroad.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AbroadEnquiryFilterDTO {
    private String continent;
    private String country;
    private String stream;
    private String course;
    private String status;
    private String fullName;
    private String branchCode;
    private String role;
    private String email;

    private String enquiryDateFilter; // today, last7days, last30days, last365days, custom
    private LocalDate startDate;
    private LocalDate endDate;

    private int page = 0;
    private int size = 10;
}