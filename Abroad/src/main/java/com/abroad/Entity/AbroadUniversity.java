package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AbroadUniversity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String universityName;

    private String image;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;

//    @OneToMany(mappedBy = "abroadUniversity", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<AbroadCollege> abroadColleges;

    @OneToMany(mappedBy = "abroadUniversity",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadEnquiry> abroadEnquiries;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private AbroadCountry abroadCountry;

    @OneToMany(mappedBy = "abroadUniversity",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadStream> abroadStream;
}
