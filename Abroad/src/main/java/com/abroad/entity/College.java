package com.abroad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import jakarta.validation.constraints.Email;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String collegeName;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses;

    @ManyToOne
    @JoinColumn(name = "stream_id")
    private Stream stream;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;
}
