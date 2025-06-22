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
public class Consultation_Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String email;
    private String phoneno;
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;

}
