package com.abroad.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

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

    // Documents
    private String sop;          // Statement of Purpose
    private String lors;         // Letters of Recommendation
    private String resume;       // CV
    private String testScores;   // Standardized Test Scores
    private String passportCopy;
    private String studentVisa;
    private String passportPhotos;

    // Common fields
    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;
    private LocalDateTime createdDateTime;
}
