package com.abroad.repository;

import com.abroad.entity.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Integer> {
    @Query("SELECT a FROM AboutUs a WHERE a.branchCode = :branchCode ORDER BY a.id DESC")
    List<AboutUs> findAllByBranchCode(@Param("branchCode") String branchCode);
}