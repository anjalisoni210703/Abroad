package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.sql.Date;

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
    private Date enquiry_date;
    private String address;
    private String landmark;
    private String state;
    private String district;
    private String continent;
    private String country;
    private String course;
    private String photoUrl;
    private String stream;
    private String university;
    private String collage;

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

}
