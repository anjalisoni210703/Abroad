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

}
