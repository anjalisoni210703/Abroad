package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AbroadCollege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String collegeName;

    @ManyToOne
    @JoinColumn(name = "university_id")
    @JsonIgnore
    private AbroadUniversity abroadUniversity;

    @OneToMany
    @JsonIgnore
    private List<AbroadEnquiry> abroadEnquiries;

//    @OneToMany(mappedBy = "abroadCollege",cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<AbroadStream> abroadStreams;

    @Email
    private String createdByEmail;
    private String role;
//    private String branchCode;
}
