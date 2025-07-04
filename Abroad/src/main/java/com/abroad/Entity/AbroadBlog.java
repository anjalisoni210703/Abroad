package com.abroad.Entity;


import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AbroadBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private String title;
    private String category;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;
}
