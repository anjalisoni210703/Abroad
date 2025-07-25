package com.abroad.Repository;

import com.abroad.Entity.AbroadState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<AbroadState, Long> {
    @Query("SELECT s FROM AbroadState s WHERE s.abroadCountry.id = :countryId ORDER BY s.id DESC")
    List<AbroadState> findAllByCountryId(@Param("countryId") Long countryId);

}