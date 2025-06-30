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
public class AbroadContinent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String continentname;

    @OneToMany(mappedBy = "abroadContinent",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadCountry> abroadCountries;

    @OneToMany(mappedBy = "abroadContinent",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadEnquiry> abroadEnquiries;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;

}
