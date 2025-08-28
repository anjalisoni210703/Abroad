package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadCourseDTO {
    private Long id;
    private String courseName;
    private Double tutionFees;
    private Double applicationFees;
    private String description;
    private LocalDate date;
    private String duration;
    private String instituteRank;
    private String thumbnail;
    private String intake;
    private String websiteLink;
    private String academicRequirements;
    private String englishExamRequirements;
    private String examScore;
    private String city;
    private String location;
    private String additionalRequirements;
    private String courseDetials;
    private String scholarship;
    private String hostel;
    private String hostelFees;
    private String contractType;
    private String examType;
    private String image;
    private String applicationLink;
    private String streamName;
    private String tutionFeesINR;
    private String feesINR;
    private String createdByEmail;
    private String role;
//    private Long collegeId; // reference only, not full object
}