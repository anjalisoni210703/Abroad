package com.abroad.Repository;

import com.abroad.Entity.AbroadUniversity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<AbroadUniversity, Long> {
//    @Query("SELECT u FROM AbroadUniversity u WHERE u.branchCode = :branchCode ORDER BY u.id DESC")
//    List<AbroadUniversity> findAllByBranchCode(@Param("branchCode") String branchCode);

    @Query("SELECT u FROM AbroadUniversity u WHERE  u.abroadCity.id = :cityId ORDER BY u.id DESC")
    List<AbroadUniversity> findAllByBranchCodeAndCountry( @Param("cityId") Long cityId);

    Optional<AbroadUniversity> findByUniversityNameIgnoreCase(String universityName);

    Optional<AbroadUniversity> findById(Long id);


    Optional<AbroadUniversity> findByUniversityName(String universityName);
    Page<AbroadUniversity> findByAbroadCityId(Long cityId, Pageable pageable);
    List<AbroadUniversity> findByUniversityNameIn(List<String> universityNames);

    // Custom query to return only university names (case-insensitive partial search)
    @Query("SELECT u.universityName FROM AbroadUniversity u WHERE LOWER(u.universityName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<String> findUniversityNamesByPartialMatch(String name);
}



