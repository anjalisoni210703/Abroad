package com.abroad.Service;

import com.abroad.Entity.AbroadCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    AbroadCategory addCategory(String role, String email, AbroadCategory category);

    AbroadCategory getCategoryById(Long id, String role, String email);

    List<AbroadCategory> getAllCategory(String role, String email);

    AbroadCategory updateCategory(Long id, String role, String email, AbroadCategory category);

    Void deleteCategory(Long id, String role, String email);
}
