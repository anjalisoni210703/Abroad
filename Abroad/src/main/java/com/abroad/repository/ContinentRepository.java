package com.abroad.repository;

import com.abroad.entity.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public  interface ContinentRepository extends JpaRepository<Continent, Long> {
    @Query("SELECT c FROM Continent c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<Continent> findAllByBranchCode(@Param("branchCode") String branchCode);
}
