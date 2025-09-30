package com.abroad.Service;

import com.abroad.DTO.*;
import com.abroad.Entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AbroadHierarchyService {

    AbroadContinentDTO getHierarchyByContinentId(Long continentId);
    List<AbroadContinentDTO> getAllHierarchies();
    List<AbroadContinentDTO> getFilteredHierarchy(
            Long continentId,
            List<String> continentNames,
            List<String> countryNames,
            List<String> stateNames,
            List<String> cityNames,
            List<String> universityNames,
            List<String> collegeNames,
            List<String> courseNames,
            List<String> streamNames,       //
            String scholarship,             //
            String feesRange,               //
            List<String> examTypes,          //
            String englishExamRequirements,       // ✅ NEW
            List<String> academicRequirements,    // ✅ NEW
            List<String> intake


    );


}