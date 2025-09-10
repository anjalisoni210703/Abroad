package com.abroad.Repository;

import com.abroad.Entity.AbroadContinent;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public  interface ContinentRepository extends JpaRepository<AbroadContinent, Long> {
//    @Query("SELECT c FROM AbroadContinent c WHERE c.branchCode = :branchCode ORDER BY c.id DESC")
//    List<AbroadContinent> findAllByBranchCode(@Param("branchCode") String branchCode);

    Optional<AbroadContinent> findByContinentnameIgnoreCase(String continentname);

    Optional<AbroadContinent> findById(Long id);

    Page<AbroadContinent> findAll(Pageable pageable);
    Optional<AbroadContinent> findByContinentname(String continentName);
    List<AbroadContinent> findByContinentnameIn(List<String> continentNames);

}
