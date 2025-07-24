package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadCourseDTO {
    private Long id;
    private String courseName;
    private Double tutionFees;
    private Double applicationFees;
}