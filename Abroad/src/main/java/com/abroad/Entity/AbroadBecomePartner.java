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

    @Pattern(regexp = "^[1-9]\\d{9}$", message = "Phone number must be upto 10 digits")
    private String mobileNo;
    private String businessContact;
    private String authorDesg;

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
