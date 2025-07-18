package com.abroad.Service;

import com.abroad.Entity.AbroadCourseName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AbroadCourseNameService {

    AbroadCourseName createCourseName(String role, String email, AbroadCourseName collegeName);

    AbroadCourseName getCourseNameById(Long id, String role, String email);

    List<AbroadCourseName> getAllCourseName(String role, String email);

    AbroadCourseName updateCourseName(Long id, String role, String email, AbroadCourseName ucourseName);

    Void deleteCourseName(Long id, String role, String email);
}
