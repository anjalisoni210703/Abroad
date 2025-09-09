package com.abroad.Service;

import com.abroad.DTO.AbroadContinentDTO;
import com.abroad.Entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AbroadHierarchyService {

    AbroadContinentDTO getHierarchyByContinentId(Long continentId);
    List<AbroadContinentDTO> getAllHierarchies();
    // Continent
    Page<AbroadContinent> getAllContinents(Pageable pageable);

    // Country
    Page<AbroadCountry> getCountriesByContinent(Long continentId, Pageable pageable);

    // State
    Page<AbroadState> getStatesByCountry(Long countryId, Pageable pageable);

    // City
    Page<AbroadCity> getCitiesByState(Long stateId, Pageable pageable);

    // University
    Page<AbroadUniversity> getUniversitiesByCity(Long cityId, Pageable pageable);

    // College
    Page<AbroadCollege> getCollegesByUniversity(Long universityId, Pageable pageable);

    // Course
    Page<AbroadCourse> getCoursesByCollege(Long collegeId, Pageable pageable);

    

}