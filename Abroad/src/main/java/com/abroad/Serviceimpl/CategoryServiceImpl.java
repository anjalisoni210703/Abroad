package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCategory;
import com.abroad.Repository.CategoryRepository;
import com.abroad.Service.CategoryService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadCategory addCategory(String role, String email, AbroadCategory category){
        if(!permissionService.hasPermission(role,email,"Post")){
            throw new RuntimeException("Access Denied");
        }
        category.setCreatedByEmail(email);
        category.setRole(role);
        return categoryRepository.save(category);
    }

    @Override
    public AbroadCategory getCategoryById(Long id, String role, String email){
        if(!permissionService.hasPermission(role,email,"Get")){
            throw new RuntimeException("Access Denied");
        }
        return categoryRepository.findById(id).get();
    }

    @Override
    public List<AbroadCategory> getAllCategory(String role, String email){
        if(!permissionService.hasPermission(role,email,"Get")){
            throw new RuntimeException("Access Denied");
        }
        return categoryRepository.findAll();
    }

    @Override
    public AbroadCategory updateCategory(Long id, String role, String email, AbroadCategory category){
        if(!permissionService.hasPermission(role,email,"Put")){
            throw new RuntimeException("Access Denied");
        }
        AbroadCategory category1=categoryRepository.findById(id).get();
        category1.setRole(role);
        category1.setCreatedByEmail(email);
        category1.setCategory(category.getCategory()!=null?category.getCategory():category1.getCategory());
        return categoryRepository.save(category1);
    }

    @Override
    public Void deleteCategory(Long id, String role, String email){
        if(!permissionService.hasPermission(role,email,"Delete")){
            throw new RuntimeException("Access Denied");
        }
        categoryRepository.deleteById(id);
        return null;
    }
}
