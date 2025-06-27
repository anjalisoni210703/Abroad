package com.abroad.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AbroadAboutUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String aboutUsTitle;
    private String aboutUsDescription;
    private String aboutUsImage;

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;
}
