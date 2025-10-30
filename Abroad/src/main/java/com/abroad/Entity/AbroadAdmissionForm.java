package com.abroad.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(
        name = "abroad_admission_form",
        uniqueConstraints = @UniqueConstraint(columnNames = {"email"})
)
public class AbroadAdmissionForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic details
    private String fullName;
    @Email
    private String email;
    private String phone;
    private String alternatePhone;

    // Academic details
    private String country;
    private String university;
    private String course;
    private String stream;
    private String passoutYear;
    private String status;
    private String intake;
    private String notes;
    private String gender;
    private LocalDate dob;
    private LocalDate enquiryDate;
    private String passportAvailable;
    private String passedCourse;
    private String percentage;
    private String gapInEducation;
    private String applyFor;
    private String continent;
    private String state;
    private String city;
    private String college;
    private String houseNumber;
    private String streetName;
    private String landmark;
    private String pincode;
    private String fatherOccupation;
    private String fatherIncome;
    private String fatherPhone;
    private String assignedStaffName;
    private String assignedStaffEmail;


    // Documents
    private String sop;                                 // Statement of Purpose
    private String sop2;                                // Additional SOP
    private String fatherITR1;
    private String fatherITR2;
    private String fatherITR3;
    private String lors;
    private String lorsTranscript2;
    private String resume;                              // CV
    private String testScores;                          // Standardized Test Scores
    private String passportCopy;
    private String passportInHandPhoto;                 // Photo with Passport in Hand
    private String studentVisa;
    private String passportPhotos;
    private String moiCertificate;                      // Renamed to follow Java naming convention
    private String moiWithSealAndSign;                  // MOI with Principal’s Seal/Sign
    private String workOrInternshipExperienceCertificate;
    private String tenthDigitalMarksheet;               // 10th Digital Marksheet
    private String twelfthDigitalMarksheet;             // 12th Digital Marksheet
    private String degreeMarkList;
    private String diplomaMarkList;
    private String transcripts;                         // Fixed naming convention (was "Transcripts")
    private String bonafideCertificate;
    private String fatherPanCard;
    private String fatherIDProof;
    private String motherIDProof;
    private String bankStatement;                       // Fixed case (was "BankStatement")
    private String fatherBankStatement;
    private String bankBalanceCertificate;              // BBC

    // Common fields
    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;
    private LocalDateTime createdDateTime;
}
