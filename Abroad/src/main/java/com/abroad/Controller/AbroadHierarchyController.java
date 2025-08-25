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

}
