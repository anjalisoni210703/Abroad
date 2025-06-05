package com.abroad.controller;

import com.abroad.entity.Continent;
import com.abroad.service.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContinentController {
    @Autowired
    private ContinentService continentService;

    @PostMapping("/createContinent")
    public ResponseEntity<Continent> createContinent(@RequestBody Continent continent) {
        return ResponseEntity.ok(continentService.createContinent(continent));
    }

    @GetMapping("/getAllContinents")
    public ResponseEntity<List<Continent>> getAllContinents() {
        return ResponseEntity.ok(continentService.getAllContinents());
    }

    @GetMapping("/getContinentById/{id}")
    public ResponseEntity<Continent> getContinentById(@PathVariable Long id) {
        return ResponseEntity.ok(continentService.getContinentById(id));
    }

    @PutMapping("/updateContinent/{id}")
    public ResponseEntity<Continent> updateContinent(@PathVariable Long id, @RequestBody Continent continent) {
        return ResponseEntity.ok(continentService.updateContinent(id, continent));
    }

    @DeleteMapping("/deleteContinent/{id}")
    public ResponseEntity<String> deleteContinent(@PathVariable Long id) {
        continentService.deleteContinent(id);
        return ResponseEntity.ok("Continent deleted successfully.");
    }
}
