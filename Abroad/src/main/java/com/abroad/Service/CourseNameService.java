package com.abroad.Service;

import com.abroad.Entity.CourseName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseNameService {

    CourseName createCollegeName(String role, String email, CourseName collegeName);

    CourseName getCollegeNameById(Long id, String role, String email);

    List<CourseName> getAll(String role, String email);

    CourseName update(Long id, String role, String email, CourseName ucourseName);

    Void delete(Long id, String role, String email);
}
