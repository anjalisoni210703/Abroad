package com.abroad.Repository;

import com.abroad.Entity.AbroadCity;
import com.abroad.Entity.AbroadState;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<AbroadState, Long> {
    @Query("SELECT s FROM AbroadState s WHERE s.abroadCountry.id = :countryId ORDER BY s.id DESC")
    List<AbroadState> findAllByAbroadCountryId(@Param("countryId") Long countryId);

    Optional<AbroadState> findByState(String stateName);
    Page<AbroadState> findByAbroadCountryId(Long countryId, Pageable pageable);

    List<AbroadState> findByStateIn(List<String> stateNames);

    @Query("SELECT s.state FROM AbroadState s WHERE LOWER(s.state) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<String> findStateNamesByPartialMatch(String name);

}