package com.abroad.repository;

import com.abroad.entity.Footer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FooterRepository extends JpaRepository<Footer, Long> {
    @Query("SELECT f FROM Footer f WHERE f.branchCode = :branchCode ORDER BY f.footerId DESC")
    List<Footer> findAllByBranchCode(@Param("branchCode") String branchCode);
}
