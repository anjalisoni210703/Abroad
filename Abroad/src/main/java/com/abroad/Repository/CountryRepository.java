package com.abroad.Repository;

import com.abroad.Entity.AbroadCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<AbroadCountry, Long> {
    @Query("SELECT c FROM AbroadCountry c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
    List<AbroadCountry> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT c FROM AbroadCountry c WHERE c.id = :id AND c.branchCode = :branchCode")
    Optional<AbroadCountry> findByIdAndBranchCode(@Param("id") Long id, @Param("branchCode") String branchCode);
}
