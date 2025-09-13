package com.abroad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbroadCollegeDTO {
    private Long id;
    private String collegeName;
    private List<AbroadCourseDTO> abroadCourses;
}