package com.abroad.Service;

import com.abroad.Entity.AbroadCourseName;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public interface AbroadCourseNameService {

    AbroadCourseName createCourseName(String role, String email, AbroadCourseName collegeName);

    AbroadCourseName getCourseNameById(Long id, String role, String email);

    List<AbroadCourseName> getAllCourseName();

    AbroadCourseName updateCourseName(Long id, String role, String email, AbroadCourseName ucourseName);

    Void deleteCourseName(Long id, String role, String email);
}
