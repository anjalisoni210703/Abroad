package com.abroad.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
    private String conductedBy;
    private String contractType;
    private String instituteType;
    private String university;
    private String commissionPercent;
    private String remark;
    private String designation;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNo;
    private String businessContact;
    private String authorityDesignation;
    private String authorityName;
    private String authorityEmail;
    private String authorityContact;

    @Email
    private String businessEmail;

    private String contractPdf;
    private String commissionPdf;
    private String panPdf;
    private String gstPdf;

    @Email
    private String createdByEmail;
    private String role;
//    private String branchCode;
}
