package com.abroad.service;

import com.abroad.entity.University;

import java.util.List;

public interface UniversityService {
    University saveUniversity(University university);
    List<University> getAllUniversities();
    University getUniversityById(Long id);
    University updateUniversity(Long id, University updatedUniversity);

}
