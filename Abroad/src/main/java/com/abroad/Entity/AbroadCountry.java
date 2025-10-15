package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String image;

    @ManyToOne
    @JoinColumn(name = "continent_id")
    @JsonIgnore
    private AbroadContinent abroadContinent;

//    @OneToMany(mappedBy = "abroadCountry",cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<AbroadUniversity> university;

    @OneToMany(mappedBy = "abroadCountry",cascade =CascadeType.ALL )
    @JsonIgnore
    private List<AbroadEnquiry> abroadEnquiries;

    @OneToMany(mappedBy = "abroadCountry",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadState> abroadStates;

    @OneToMany(mappedBy = "abroadCountry",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadLead> abroadLeads;

    @Email
    private String createdByEmail;
    private String role;

//  private String branchCode;
}
