package com.abroad.Serviceimpl;

import com.abroad.Entity.CourseName;
import com.abroad.Repository.CourseNameRepository;
import com.abroad.Service.CourseNameService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseNameServiceImpl implements CourseNameService {

    @Autowired
    private CourseNameRepository courseNameRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public CourseName createCollegeName(String role, String email, CourseName courseName){
        if(!permissionService.hasPermission(role,email,"Post")){
            throw new RuntimeException("AccessDenied");
        }
        courseName.setRole(role);
        courseName.setEmail(email);
        return courseNameRepository.save(courseName);
    }

    @Override
    public CourseName getCollegeNameById(Long id, String role, String email){
        if(!permissionService.hasPermission(role,email,"Get")){
            throw new RuntimeException("AccessDenied");
        }
        return courseNameRepository.findById(id).get();
    }

    @Override
    public List<CourseName> getAll(String role, String email){
        if(!permissionService.hasPermission(role,email,"Get")){
            throw new RuntimeException("AccessDenied");
        }
        return courseNameRepository.findAll();
    }

    @Override
    public CourseName update(Long id, String role, String email, CourseName ucourseName){
        if(!permissionService.hasPermission(role,email,"Put")){
            throw new RuntimeException("AccessDenied");
        }
        CourseName existing=courseNameRepository.findById(id).get();
        if(ucourseName.getCourseName()!=null) existing.setCourseName(ucourseName.getCourseName());
        existing.setEmail(email);
        existing.setRole(role);
        return courseNameRepository.save(existing);
    }

    @Override
    public Void delete(Long id, String role, String email){
        if(!permissionService.hasPermission(role,email,"Delete")){
            throw new RuntimeException("AccessDenied");
        }
        courseNameRepository.deleteById(id);
        return null;
    }
}
