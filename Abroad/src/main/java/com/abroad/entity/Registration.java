package com.abroad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String mobileNumber;

    @Email
    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String image; // Optional profile image

    private String createdByEmail;
    private String role;
    private String branchCode;

}
