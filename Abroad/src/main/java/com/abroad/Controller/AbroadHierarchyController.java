package com.abroad.Controller;

import com.abroad.DTO.AbroadContinentDTO;
import com.abroad.Service.AbroadHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadHierarchyController {

    private final AbroadHierarchyService hierarchyService;

    @GetMapping("/hierarchyContinent/{id}")
    public ResponseEntity<AbroadContinentDTO> getContinentHierarchy(@PathVariable Long id) {
        return ResponseEntity.ok(hierarchyService.getHierarchyByContinentId(id));
    }
}
