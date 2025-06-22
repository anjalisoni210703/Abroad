package com.abroad.repository;

import com.abroad.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollegeRepository  extends JpaRepository<College, Long> {
    @Query("SELECT c FROM College c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<College> findAllByBranchCode(@Param("branchCode") String branchCode);
}
