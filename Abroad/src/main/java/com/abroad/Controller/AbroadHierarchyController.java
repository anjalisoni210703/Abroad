package com.abroad.Controller;

import com.abroad.DTO.AbroadContinentDTO;
import com.abroad.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadHierarchyController {

    private final AbroadHierarchyService hierarchyService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ContinentService continentService;



    // ✅ Single endpoint handling both cases
    @GetMapping("/hierarchyContinent")
    public ResponseEntity<?> getContinentHierarchy(@RequestParam(required = false) Long id) {
        if (id != null) {
            // return single continent hierarchy
            return ResponseEntity.ok(hierarchyService.getHierarchyByContinentId(id));
        } else {
            // return all continent hierarchies
            List<AbroadContinentDTO> allHierarchies = hierarchyService.getAllHierarchies();
            return ResponseEntity.ok(allHierarchies);
        }
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<?> getHierarchy(
            @RequestParam(required = false) String continentName,
            @RequestParam(required = false) String countryName,
            @RequestParam(required = false) String stateName,
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) String universityName,
            @RequestParam(required = false) String collegeName,
            Pageable pageable) {

        if (collegeName != null) {
            return ResponseEntity.ok(hierarchyService.getCoursesByCollegeName(collegeName, pageable));
        } else if (universityName != null) {
            return ResponseEntity.ok(hierarchyService.getCollegesByUniversityName(universityName, pageable));
        } else if (cityName != null) {
            return ResponseEntity.ok(hierarchyService.getUniversitiesByCityName(cityName, pageable));
        } else if (stateName != null) {
            return ResponseEntity.ok(hierarchyService.getCitiesByStateName(stateName, pageable));
        } else if (countryName != null) {
            return ResponseEntity.ok(hierarchyService.getStatesByCountryName(countryName, pageable));
        } else if (continentName != null) {
            return ResponseEntity.ok(hierarchyService.getCountriesByContinentName(continentName, pageable));
        } else {
            return ResponseEntity.ok(hierarchyService.getAllContinents(pageable));
        }
    }


}
