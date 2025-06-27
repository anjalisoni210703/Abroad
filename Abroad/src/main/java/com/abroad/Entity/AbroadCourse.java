package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AbroadCourse {
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
        @JoinColumn(name = "stream_id")
        @JsonIgnore
        private AbroadStream abroadStream;

        @OneToMany(mappedBy = "abroadCourse",cascade = CascadeType.ALL)
        @JsonIgnore
        private List<AbroadEnquiry> abroadEnquiries;

        @Email
        private String createdByEmail;
        private String role;
        private String branchCode;
    }

