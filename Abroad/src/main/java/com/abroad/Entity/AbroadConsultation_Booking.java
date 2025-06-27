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
public class AbroadConsultation_Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;
    private String email;
    private String phoneno;
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    @JsonIgnore
    private AbroadContinent abroadContinent;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private AbroadCourse abroadCourse;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;

}
