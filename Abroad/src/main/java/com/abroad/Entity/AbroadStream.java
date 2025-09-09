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


    private String name;

    private String image;

    @OneToMany(mappedBy = "abroadStream",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AbroadEnquiry> abroadEnquiries;


    @Email
    private String createdByEmail;
    private String role;

}
