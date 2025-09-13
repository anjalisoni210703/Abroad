package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadCountryDTO {
    private Long id;
    private String country;
    private List<AbroadStateDTO> abroadStates;
}