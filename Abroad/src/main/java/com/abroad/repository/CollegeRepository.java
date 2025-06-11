package com.abroad.repository;

import com.abroad.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollegeRepository  extends JpaRepository<College, Long> {
    List<College> findByUniversityId(Long universityId);
}
