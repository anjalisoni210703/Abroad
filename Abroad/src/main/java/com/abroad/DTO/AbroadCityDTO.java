package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadCityDTO {
    private Long id;
    private String city;
    private List<AbroadUniversityDTO> universities;
}