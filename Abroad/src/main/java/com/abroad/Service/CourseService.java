package com.abroad.Service;

import com.abroad.Entity.AbroadCourse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CourseService {
    AbroadCourse createCourse(AbroadCourse abroadCourse, MultipartFile image, String role, String email, Long collegeId);
    List<AbroadCourse> getAllCourses(String role, String email, Long collegeId);
    AbroadCourse getCourseById(Long id, String role, String email);
    AbroadCourse updateCourse(Long id, AbroadCourse abroadCourse, MultipartFile image, String role, String email);
    void deleteCourse(Long id, String role, String email);

//    List<AbroadCourse> filterCourses(List<Long> streamIds,
//                                     List<Long> collegeIds,
//                                     List<Long> universityIds,
//                                     List<Long> cityIds,
//                                     List<Long> stateIds,
//                                     List<Long> countryIds,
//                                     List<Long> continentIds,
//                                     String role,
//                                     String email);


}
