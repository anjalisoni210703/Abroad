package com.abroad.dto;


import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {
    private String address;
    private String landmark;
    private String state;
    private String district;
}
