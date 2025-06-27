package com.abroad.Repository;

import com.abroad.Entity.AbroadFooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FooterRepository extends JpaRepository<AbroadFooter, Long> {
    @Query("SELECT f FROM AbroadFooter f WHERE f.branchCode = :branchCode ORDER BY f.footerId DESC")
    List<AbroadFooter> findAllByBranchCode(@Param("branchCode") String branchCode);
}
