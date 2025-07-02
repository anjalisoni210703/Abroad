package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String continent;
    private String course;

    private boolean cansGet;
    private boolean cansPut;
    private boolean cansPost;
    private boolean cansDelete;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    @JsonIgnore
    private AbroadContinent abroadContinent;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private AbroadCourse abroadCourse;
}
