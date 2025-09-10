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
            @RequestParam(required = false) List<String> continentName,
            @RequestParam(required = false) List<String> countryName,
            @RequestParam(required = false) List<String> stateName,
            @RequestParam(required = false) List<String> cityName,
            @RequestParam(required = false) List<String> universityName,
            @RequestParam(required = false) List<String> collegeName,
            @RequestParam(required = false) List<String> courseName) {

        boolean noFilters =
                (continentName == null || continentName.isEmpty()) &&
                        (countryName == null || countryName.isEmpty()) &&
                        (stateName == null || stateName.isEmpty()) &&
                        (cityName == null || cityName.isEmpty()) &&
                        (universityName == null || universityName.isEmpty()) &&
                        (collegeName == null || collegeName.isEmpty()) &&
                        (courseName == null || courseName.isEmpty());

        if (noFilters) {
            return ResponseEntity.ok(hierarchyService.getAllHierarchies());
        } else {
            return ResponseEntity.ok(
                    hierarchyService.getFilteredHierarchy(
                            continentName, countryName, stateName, cityName,
                            universityName, collegeName, courseName
                    )
            );
        }
    }





}
