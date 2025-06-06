package com.abroad.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnquiryFilterDTO {
    private String name;
    private String email;
    private String batch;
    private String conducts;
    private String status;
    private Long phoneNo;
    private String sourceBy;
    private Date enquiryDate;
    private String motherTounge;
    private String fatherProfession;
    private String educationQualification;
    private Long annualIncome;
    private String remark;
    private Date dob;
    private String gender;
    private String photoUrl;


}
