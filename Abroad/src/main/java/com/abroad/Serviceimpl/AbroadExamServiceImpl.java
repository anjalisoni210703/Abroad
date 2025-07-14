package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadExam;
import com.abroad.Repository.AbroadExamRepository;
import com.abroad.Service.AbroadExamService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AbroadExamServiceImpl implements AbroadExamService {

    @Autowired
    private AbroadExamRepository abroadExamRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadExam addExam(String role, String email, AbroadExam exam){
        if(!permissionService.hasPermission(role,email,"Post")){
            throw new RuntimeException("Access Denied");
        }
        return abroadExamRepository.save(exam);
    }

    @Override
    public AbroadExam getById(String role, String email, Long id){
        return abroadExamRepository.findById(id).get();
    }

    @Override
    public List<AbroadExam> getAll(String role, String email){
        return abroadExamRepository.findAll();
    }

    @Override
    public AbroadExam updateExam(Long id, String role, String email, AbroadExam uexam){
        if(!permissionService.hasPermission(role,email,"Put")){
            throw new RuntimeException("Access Denied");
        }
        AbroadExam existingExam=abroadExamRepository.findById(id).get();
        existingExam.setExamName(uexam.getExamName());
        return  abroadExamRepository.save(existingExam);
    }

    @Override
    public Void deleteExam(Long id, String role, String email){
        if(!permissionService.hasPermission(role,email,"Delete")){
            throw new RuntimeException("Access Denied");
        }
        abroadExamRepository.deleteById(id);
        return null;
    }
}
