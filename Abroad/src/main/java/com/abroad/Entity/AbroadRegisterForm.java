package com.abroad.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AbroadRegisterForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String mobileNumber;
    private String stream;
    private String courseName;
    private String location;
    private Double amount;

    // Add these fields:
    private String branchCode;
    private String role;
    private String createdByEmail;
}
