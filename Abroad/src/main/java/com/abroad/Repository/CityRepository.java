package com.abroad.Repository;

import com.abroad.Entity.AbroadCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<AbroadCity, Long> {

    @Query("SELECT c FROM AbroadCity c WHERE c.abroadState.id = :stateId ORDER BY c.id DESC")
    List<AbroadCity> findAllByStateId(Long stateId);
}