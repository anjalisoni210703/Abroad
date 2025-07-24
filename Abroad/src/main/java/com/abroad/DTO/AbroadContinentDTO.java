package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadContinentDTO {
    private Long id;
    private String continentname;
    private List<AbroadCountryDTO> countries;
}
