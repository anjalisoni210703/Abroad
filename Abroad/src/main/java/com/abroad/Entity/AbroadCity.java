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
public class AbroadCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String image;

    @Email
    private String createdByEmail;
    private String role;

    @ManyToOne
    @JoinColumn(name = "state_id")
    @JsonIgnore
    private AbroadState abroadState;

    @OneToMany(mappedBy = "abroadCity",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadUniversity> abroadUniversities;

    @OneToMany(mappedBy = "abroadCity",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadEnquiry> abroadEnquiries;
}
