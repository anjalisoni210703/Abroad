// AbroadHierarchyController.java
package com.abroad.Controller;

import com.abroad.DTO.AbroadContinentDTO;
import com.abroad.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadHierarchyController {

    private final AbroadHierarchyService hierarchyService;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private StreamService streamService;



    // The single, unified API endpoint as per your documentation
    @GetMapping("/hierarchy")
    public ResponseEntity<List<AbroadContinentDTO>> getHierarchy(
            @RequestParam(required = false) Long continentId,
            @RequestParam(required = false) List<String> continentName,
            @RequestParam(required = false) List<String> countryName,
            @RequestParam(required = false) List<String> stateName,
            @RequestParam(required = false) List<String> cityName,
            @RequestParam(required = false) List<String> universityName,
            @RequestParam(required = false) List<String> collegeName,
            @RequestParam(required = false) List<String> courseName,
            @RequestParam(required = false) List<String> streamName,
            @RequestParam(required = false) String scholarship,
            @RequestParam(required = false) String feesRange,
            @RequestParam(required = false) List<String> examType,
            @RequestParam(required = false) String englishExamRequirements,  // ✅ New
            @RequestParam(required = false) List<String> academicRequirements, // ✅ New
            @RequestParam(required = false) List<String> intake               // ✅ New
    ) {

        boolean noFilters =
                (continentId == null) &&
                        (continentName == null || continentName.isEmpty()) &&
                        (countryName == null || countryName.isEmpty()) &&
                        (stateName == null || stateName.isEmpty()) &&
                        (cityName == null || cityName.isEmpty()) &&
                        (universityName == null || universityName.isEmpty()) &&
                        (collegeName == null || collegeName.isEmpty()) &&
                        (courseName == null || courseName.isEmpty()) &&
                        (streamName == null || streamName.isEmpty()) &&
                        (scholarship == null || scholarship.isEmpty()) &&
                        (feesRange == null || feesRange.isEmpty()) &&
                        (examType == null || examType.isEmpty()) &&
                        (englishExamRequirements == null || englishExamRequirements.isEmpty()) && // ✅
                        (academicRequirements == null || academicRequirements.isEmpty()) &&       // ✅
                        (intake == null || intake.isEmpty());                                     // ✅

        if (noFilters) {
            // Case 1: Get All Hierarchies
            return ResponseEntity.ok(hierarchyService.getAllHierarchies());
        } else if (continentId != null &&
                (continentName == null || continentName.isEmpty()) &&
                (countryName == null || countryName.isEmpty()) &&
                (stateName == null || stateName.isEmpty()) &&
                (cityName == null || cityName.isEmpty()) &&
                (universityName == null || universityName.isEmpty()) &&
                (collegeName == null || collegeName.isEmpty()) &&
                (courseName == null || courseName.isEmpty()) &&
                (streamName == null || streamName.isEmpty()) &&
                (scholarship == null || scholarship.isEmpty()) &&
                (feesRange == null || feesRange.isEmpty()) &&
                (examType == null || examType.isEmpty()) &&
                (englishExamRequirements == null || englishExamRequirements.isEmpty()) &&
                (academicRequirements == null || academicRequirements.isEmpty()) &&
                (intake == null || intake.isEmpty())) {
            // Case 2: Only continentId filter
            return ResponseEntity.ok(List.of(hierarchyService.getHierarchyByContinentId(continentId)));
        } else {
            // Case 3: All other filtered scenarios
            List<AbroadContinentDTO> filteredHierarchies =
                    hierarchyService.getFilteredHierarchy(
                            continentId,
                            continentName, countryName, stateName, cityName,
                            universityName, collegeName, courseName,
                            streamName, scholarship, feesRange, examType,
                            englishExamRequirements, academicRequirements, intake  // ✅ Added
                    );
            return ResponseEntity.ok(filteredHierarchies);
        }
    }
/// /->

    @GetMapping("/searchUniversities")
    public ResponseEntity<List<String>> searchUniversities(@RequestParam String name) {
        List<String> results = universityService.searchUniversityNames(name);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/searchStates")
    public ResponseEntity<List<String>> searchStates(@RequestParam String name) {
        List<String> results = stateService.searchStateNames(name);
        return ResponseEntity.ok(results);
    }

    // ✅ Search API returning only matching city names
    @GetMapping("/searchCities")
    public ResponseEntity<List<String>> searchCities(@RequestParam String name) {
        List<String> results = cityService.searchCityNames(name);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/searchColleges")
    public ResponseEntity<List<String>> searchColleges(@RequestParam String name) {
        List<String> results = collegeService.searchCollegeNames(name);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/searchCountries")
    public ResponseEntity<List<String>> searchCountries(@RequestParam String name) {
        List<String> results = countryService.searchCountryNames(name);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/searchStreams")
    public ResponseEntity<List<String>> searchStreams(@RequestParam String name) {
        List<String> results = streamService.searchStreamNames(name);
        return ResponseEntity.ok(results);
    }

    // Health check endpoint
    @GetMapping("/hierarchy/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Hierarchy service is running");
    }
}