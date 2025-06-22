package com.abroad.repository;

import com.abroad.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<Course> findAllByBranchCode(@Param("branchCode") String branchCode);
}
