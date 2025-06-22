package com.abroad.entity;

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
public class Footer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long footerId;

    private String title;
    private String footerColor;

    private String instagramLink;
    private String facebookLink;
    private String twitterLink;
    private String youtubeLink;
    private String whatsappLink;

    private String email;
    private String mobileNumber;
    private String address;

    private String image; // Optional image upload (e.g., logo)

    @Email
    private String createdByEmail;
    private String role;
    private String branchCode;

}
