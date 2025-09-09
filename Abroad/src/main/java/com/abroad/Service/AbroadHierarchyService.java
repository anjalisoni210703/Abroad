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


    // Country by Continent Name
    Page<AbroadCountry> getCountriesByContinentName(String continentName, Pageable pageable);

    // State by Country Name
    Page<AbroadState> getStatesByCountryName(String countryName, Pageable pageable);

    // City by State Name
    Page<AbroadCity> getCitiesByStateName(String stateName, Pageable pageable);

    // University by City Name
    Page<AbroadUniversity> getUniversitiesByCityName(String cityName, Pageable pageable);

    // College by University Name
    Page<AbroadCollege> getCollegesByUniversityName(String universityName, Pageable pageable);

    // Course by College Name
    Page<AbroadCourse> getCoursesByCollegeName(String collegeName, Pageable pageable);

    

}