package com.abroad.entity;

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
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long phone_no;
    private String email;
    private String batch;
    private String sourceby;
    private String conducts;
    private String status;
    private Date enquiry_date;
    private String remark;
    private Date dob;
    private String gender;
    private String mothertounge;
    private String fatherprofession;
    private String educationqualification;
    private Long annualincome;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;

}
