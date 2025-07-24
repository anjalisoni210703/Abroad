package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadStateDTO {
    private Long id;
    private String state;
    private List<AbroadCityDTO> cities;
}