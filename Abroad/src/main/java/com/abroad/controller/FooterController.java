package com.abroad.controller;

import com.abroad.entity.Footer;
import com.abroad.service.FooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FooterController {
    @Autowired
    private FooterService footerService;

    @PostMapping("/createFooter")
    public ResponseEntity<Footer> createFooter(@RequestBody Footer footer) {
        return ResponseEntity.ok(footerService.createFooter(footer));
    }

    @GetMapping("/getAllFooters")
    public ResponseEntity<List<Footer>> getAllFooters() {
        return ResponseEntity.ok(footerService.getAllFooters());
    }

    @GetMapping("/getFooterById/{id}")
    public ResponseEntity<Footer> getFooterById(@PathVariable Long id) {
        return ResponseEntity.ok(footerService.getFooterById(id));
    }

    @PutMapping("/updateFooter/{id}")
    public ResponseEntity<Footer> updateFooter(@PathVariable Long id, @RequestBody Footer footer) {
        return ResponseEntity.ok(footerService.updateFooter(id, footer));
    }

    @DeleteMapping("/deleteFooter/{id}")
    public ResponseEntity<String> deleteFooter(@PathVariable Long id) {
        footerService.deleteFooter(id);
        return ResponseEntity.ok("Footer deleted successfully.");
    }
}
