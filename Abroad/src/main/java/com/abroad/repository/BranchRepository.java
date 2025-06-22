package com.abroad.repository;

import com.abroad.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b WHERE b.branchCode = :branchCode ORDER BY b.id DESC")
    List<Branch> findAllByBranchCode(@Param("branchCode") String branchCode);
}
