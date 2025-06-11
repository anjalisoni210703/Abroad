package com.abroad.service;

import com.abroad.entity.College;

import java.util.List;

public interface CollegeService {
    College saveCollege(College college);
    List<College> getAllColleges();
    List<College> getCollegesByUniversityId(Long universityId);
    College updateCollege(Long id, College updatedCollege);

}
