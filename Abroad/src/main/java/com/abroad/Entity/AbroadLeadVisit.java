package com.abroad.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadLeadVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate visitDate;
    private String remark;
    private String status;
    private String visitCount;

    @ManyToOne
    @JoinColumn(name="enquiry_id",nullable = false)
    @JsonBackReference
    private AbroadEnquiry enquiry;

    @Email
    private String createdByEmail;
    private String role;
}
