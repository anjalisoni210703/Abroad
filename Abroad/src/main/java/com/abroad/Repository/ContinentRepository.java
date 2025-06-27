package com.abroad.Repository;

import com.abroad.Entity.AbroadContinent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public  interface ContinentRepository extends JpaRepository<AbroadContinent, Long> {
    @Query("SELECT c FROM AbroadContinent c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<AbroadContinent> findAllByBranchCode(@Param("branchCode") String branchCode);
}
