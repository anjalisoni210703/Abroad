package com.abroad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "streams")
@Getter
@Setter
@NoArgsConstructor
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String image;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;
}
