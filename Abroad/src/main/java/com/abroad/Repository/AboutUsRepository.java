package com.abroad.Repository;

import com.abroad.Entity.AbroadAboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutUsRepository extends JpaRepository<AbroadAboutUs, Integer> {
    @Query("SELECT a FROM AbroadAboutUs a WHERE a.branchCode = :branchCode ORDER BY a.id DESC")
    List<AbroadAboutUs> findAllByBranchCode(@Param("branchCode") String branchCode);
}