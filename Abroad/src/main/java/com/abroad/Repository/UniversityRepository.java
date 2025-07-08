package com.abroad.Repository;

import com.abroad.Entity.AbroadUniversity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<AbroadUniversity, Long> {
//    @Query("SELECT u FROM AbroadUniversity u WHERE u.branchCode = :branchCode ORDER BY u.id DESC")
//    List<AbroadUniversity> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT u FROM AbroadUniversity u WHERE  u.abroadCountry.id = :countryId ORDER BY u.id DESC")
    List<AbroadUniversity> findAllByBranchCodeAndCountry( @Param("countryId") Long countryId);

    Optional<AbroadUniversity> findByUniversityNameIgnoreCase(String universityName);

}
