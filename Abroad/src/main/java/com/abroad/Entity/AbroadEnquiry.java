package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AbroadEnquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long phone_no;
    private String email;
    private LocalDate enquiry_date;
    private String address;
    private String landmark;
    private String city;
    private String state;
    private String pincode;
    private String district;
    private String continent;
    private String country;
    private String course;
    private String photoUrl;
    private String stream;
    private String university;
    private String collage;
    private double percentage;
    private String status;
    private String passoutYear;
    private String passoutCourse;
    private String fatherNumber;
    private double fathersIncome;
    private String fathersOccupation;
    private String gender;
    private Date dob;
    private String applyFor;
    private String passportNo;
    private String source;
    private String loanRequirement;
    private String year;
    private String amount;
    private String remark;
    private String conductBy;
    private String gap;
    private String gapYear;
    private String entranceExam;
    private String score;
    private String fatherITR;
    private String  yearITR;
    private String amountITR;
    private String  hasPassport;

    private String document1;
    private String document2;


//    @ElementCollection
//    private List<String> uploadDocuments;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private AbroadCourse abroadCourse;

    @ManyToOne
    @JoinColumn(name = "stream_id")
    @JsonIgnore
    private AbroadStream abroadStream;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    @JsonIgnore
    private AbroadContinent abroadContinent;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private AbroadCountry abroadCountry;

    @ManyToOne
    @JoinColumn(name = "university_id")
    @JsonIgnore
    private AbroadUniversity abroadUniversity;

    @ManyToOne
    @JoinColumn(name = "college_id")
    @JsonIgnore
    private AbroadCollege abroadCollege;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private AbroadCity abroadCity;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonIgnore
    private AbroadState abroadState;

    @OneToMany(mappedBy = "enquiry",cascade = CascadeType.ALL,fetch =FetchType.LAZY )
    @JsonIgnore
    private List<AbroadLeadVisit> leadVisits;
}
