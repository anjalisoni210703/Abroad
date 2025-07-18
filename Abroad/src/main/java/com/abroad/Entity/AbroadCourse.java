package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AbroadCourse {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String courseName;
        private Double tutionFees;
        private Double applicationFees;

        @Column(length = 1000)
        private String description;

        private LocalDate date;
        private String Duration;
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

//        @ManyToOne
//        @JoinColumn(name = "stream_id")
//        @JsonIgnore
//        private AbroadStream abroadStream;
        @ManyToOne
        @JoinColumn(name = "college_id")
        @JsonIgnore
        private AbroadCollege abroadCollege;

        @OneToMany(mappedBy = "abroadCourse",cascade = CascadeType.ALL)
        @JsonIgnore
        private List<AbroadEnquiry> abroadEnquiries;

        @OneToMany(mappedBy = "abroadCourse",cascade = CascadeType.ALL)
        @JsonIgnore
        private List<AbroadUser> abroadUsers;

        @OneToMany(mappedBy = "abroadCourse",cascade = CascadeType.ALL)
        @JsonIgnore
        private List<AbroadLead> abroadLeads;

        @Email
        private String createdByEmail;
        private String role;
//        private String branchCode;
    }

