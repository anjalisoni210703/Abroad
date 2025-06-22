package com.abroad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String courseName;
        private Double price;
        private Double discountPrice;

        @Column(length = 1000)
        private String description;

        private LocalDate date;
        private String validity;
        private String categoryName;
        private String thumbnail;

        @ManyToOne
        @JoinColumn(name = "college_id")
        private College college;

        @Email
        private String createdByEmail;
        private String role;
        private String branchCode;
    }

