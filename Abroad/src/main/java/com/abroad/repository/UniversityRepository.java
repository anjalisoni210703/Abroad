package com.abroad.repository;

import com.abroad.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UniversityRepository extends JpaRepository<University, Long> {
    @Query("SELECT u FROM University u WHERE u.branchCode = :branchCode ORDER BY u.id DESC")
    List<University> findAllByBranchCode(@Param("branchCode") String branchCode);
}
