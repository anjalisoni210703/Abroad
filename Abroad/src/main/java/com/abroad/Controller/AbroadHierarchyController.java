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
            @RequestParam(required = false) Long continentId,
            @RequestParam(required = false) Long countryId,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long universityId,
            @RequestParam(required = false) Long collegeId,
            Pageable pageable) {

        if (collegeId != null) {
            return ResponseEntity.ok(hierarchyService.getCoursesByCollege(collegeId, pageable));
        } else if (universityId != null) {
            return ResponseEntity.ok(hierarchyService.getCollegesByUniversity(universityId, pageable));
        } else if (cityId != null) {
            return ResponseEntity.ok(hierarchyService.getUniversitiesByCity(cityId, pageable));
        } else if (stateId != null) {
            return ResponseEntity.ok(hierarchyService.getCitiesByState(stateId, pageable));
        } else if (countryId != null) {
            return ResponseEntity.ok(hierarchyService.getStatesByCountry(countryId, pageable));
        } else if (continentId != null) {
            return ResponseEntity.ok(hierarchyService.getCountriesByContinent(continentId, pageable));
        } else {
            return ResponseEntity.ok(hierarchyService.getAllContinents(pageable));
        }
    }


}
