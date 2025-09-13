package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadUniversityDTO {
    private Long id;
    private String universityName;
    private List<AbroadCollegeDTO> abroadColleges;
}