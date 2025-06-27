package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AbroadRegistration {
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
    @JsonIgnore
    private AbroadContinent abroadContinent;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private AbroadCourse abroadCourse;

    private String image; // Optional profile image

    private String createdByEmail;
    private String role;
    private String branchCode;

}
