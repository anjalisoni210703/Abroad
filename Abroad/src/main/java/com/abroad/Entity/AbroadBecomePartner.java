package com.abroad.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbroadBecomePartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;
    private String partnerName;
    private String partnerContact;
    private String partnerEmail;
    private String partnerPassword;
    private String partnerAddress;
    private String partnerCountry;
    private String partnerCity;
    private String partnerDistrict;
    private String partnerPincode;
    private String state;
    private String status;

    @Email
    private String createdByEmail;
    private String role;
//    private String branchCode;
}
