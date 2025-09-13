// AbroadHierarchyController.java
package com.abroad.Controller;

import com.abroad.DTO.AbroadContinentDTO;
import com.abroad.Service.AbroadHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadHierarchyController {

    private final AbroadHierarchyService hierarchyService;

    // The single, unified API endpoint as per your documentation
    @GetMapping("/hierarchy")
    public ResponseEntity<List<AbroadContinentDTO>> getHierarchy(
            @RequestParam(required = false) Long continentId, // Added continentId here
            @RequestParam(required = false) List<String> continentName,
            @RequestParam(required = false) List<String> countryName,
            @RequestParam(required = false) List<String> stateName,
            @RequestParam(required = false) List<String> cityName,
            @RequestParam(required = false) List<String> universityName,
            @RequestParam(required = false) List<String> collegeName,
            @RequestParam(required = false) List<String> courseName) {

        boolean noFilters =
                (continentId == null) && // Check continentId
                        (continentName == null || continentName.isEmpty()) &&
                        (countryName == null || countryName.isEmpty()) &&
                        (stateName == null || stateName.isEmpty()) &&
                        (cityName == null || cityName.isEmpty()) &&
                        (universityName == null || universityName.isEmpty()) &&
                        (collegeName == null || collegeName.isEmpty()) &&
                        (courseName == null || courseName.isEmpty());

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
                (courseName == null || courseName.isEmpty())) {
            // Case 2: Get Specific Continent by ID (no other filters)
            // Note: getFilteredHierarchy can handle this, but for explicit case,
            // we can call getHierarchyByContinentId for a single DTO response.
            // If getFilteredHierarchy returns a list, this needs to return a list of 1.
            return ResponseEntity.ok(List.of(hierarchyService.getHierarchyByContinentId(continentId)));
        }
        else {
            // Case 3: All other filtered scenarios (Parent-level, Child-level, Multi-level, Multiple values)
            List<AbroadContinentDTO> filteredHierarchies = hierarchyService.getFilteredHierarchy(
                    continentId, // Pass continentId to the filtered service method
                    continentName, countryName, stateName, cityName,
                    universityName, collegeName, courseName
            );
            return ResponseEntity.ok(filteredHierarchies);
        }
    }

    // Health check endpoint
    @GetMapping("/hierarchy/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Hierarchy service is running");
    }
}