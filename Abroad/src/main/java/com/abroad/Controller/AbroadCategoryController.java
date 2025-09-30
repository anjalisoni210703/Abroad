package com.abroad.Controller;

import com.abroad.Entity.AbroadCategory;
import com.abroad.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class AbroadCategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<AbroadCategory> addCategory(@RequestParam String role,
                                                      @RequestParam String email,
                                                      @RequestBody AbroadCategory category){
        return ResponseEntity.ok(categoryService.addCategory(role, email, category));
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<AbroadCategory> getCategoryById(@PathVariable Long id,
                                                          @RequestParam String role,
                                                          @RequestParam String email){
        return ResponseEntity.ok(categoryService.getCategoryById(id, role, email));
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<?> getAllCategory(@RequestParam String role,
                                                      @RequestParam String email){
        return ResponseEntity.ok(categoryService.getAllCategory(role, email));
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<AbroadCategory> updateCategory(@PathVariable Long id,
                                                         @RequestParam String role,
                                                         @RequestParam String email,
                                                         @RequestBody AbroadCategory category){
        return ResponseEntity.ok(categoryService.updateCategory(id, role, email, category));
    }
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id,
                               @RequestParam String role,
                               @RequestParam String email){
        return ResponseEntity.ok(categoryService.deleteCategory(id, role, email));

    }



}
