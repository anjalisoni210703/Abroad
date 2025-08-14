package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCourseName;
import com.abroad.Repository.AbroadCourseNameRepository;
import com.abroad.Service.AbroadCourseNameService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadCourseNameServiceImpl implements AbroadCourseNameService {

    @Autowired
    private AbroadCourseNameRepository courseNameRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadCourseName createCourseName(String role, String email, AbroadCourseName courseName){
        if(!permissionService.hasPermission(role,email,"Post")){
            throw new RuntimeException("AccessDenied");
        }
        courseName.setRole(role);
        courseName.setEmail(email);
        return courseNameRepository.save(courseName);
    }

    @Override
    public AbroadCourseName getCourseNameById(Long id, String role, String email){
        if(!permissionService.hasPermission(role,email,"Get")){
            throw new RuntimeException("AccessDenied");
        }
        return courseNameRepository.findById(id).get();
    }

    @Override
    public List<AbroadCourseName> getAllCourseName(){
//        if(!permissionService.hasPermission(role,email,"Get")){
//            throw new RuntimeException("AccessDenied");
//        }
        return courseNameRepository.findAll();
    }

    @Override
    public AbroadCourseName updateCourseName(Long id, String role, String email, AbroadCourseName ucourseName){
        if(!permissionService.hasPermission(role,email,"Put")){
            throw new RuntimeException("AccessDenied");
        }
        AbroadCourseName existing=courseNameRepository.findById(id).get();
        if(ucourseName.getCourseName()!=null) existing.setCourseName(ucourseName.getCourseName());
        existing.setEmail(email);
        existing.setRole(role);
        return courseNameRepository.save(existing);
    }

    @Override
    public Void deleteCourseName(Long id, String role, String email){
        if(!permissionService.hasPermission(role,email,"Delete")){
            throw new RuntimeException("AccessDenied");
        }
        courseNameRepository.deleteById(id);
        return null;
    }
}
