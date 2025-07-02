package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "streams")
@Getter
@Setter
@NoArgsConstructor
public class AbroadStream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String image;

//    @ManyToOne
//    @JoinColumn(name = "collage_id")
//    @JsonIgnore
//    private AbroadCollege abroadCollege;

    @OneToMany(mappedBy ="abroadStream",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadCourse> abroadCourses;

    @OneToMany(mappedBy = "abroadStream",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadEnquiry> abroadEnquiries;

    @ManyToOne
    @JoinColumn(name = "university_id")
    @JsonIgnore
    private AbroadUniversity abroadUniversity;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;
}
