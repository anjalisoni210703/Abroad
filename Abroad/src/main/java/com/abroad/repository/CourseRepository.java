package com.abroad.repository;

import com.abroad.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCollegeId(Long collegeId);
    @Query("SELECT c FROM Course c WHERE c.college.university.id = :universityId AND c.college.stream.id = :streamId")
    List<Course> findCoursesByUniversityAndStream(Long universityId, Long streamId);

}
