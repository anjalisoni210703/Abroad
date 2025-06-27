package com.abroad.Repository;

import com.abroad.Entity.AbroadUniversity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UniversityRepository extends JpaRepository<AbroadUniversity, Long> {
    @Query("SELECT u FROM AbroadUniversity u WHERE u.branchCode = :branchCode ORDER BY u.id DESC")
    List<AbroadUniversity> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT u FROM AbroadUniversity u WHERE u.branchCode = :branchCode AND u.abroadCountry.id = :countryId ORDER BY u.id DESC")
    List<AbroadUniversity> findAllByBranchCodeAndCountry(@Param("branchCode") String branchCode, @Param("countryId") Long countryId);
}
