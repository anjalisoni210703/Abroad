package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadExam;
import com.abroad.Repository.AbroadExamRepository;
import com.abroad.Service.AbroadExamService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadExamServiceImpl implements AbroadExamService {

    @Autowired
    private AbroadExamRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadExam createExam(AbroadExam exam, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Exam");
        }

        exam.setCreatedByEmail(email);
        exam.setRole(role);
        return repository.save(exam);
    }

    @Override
    public List<AbroadExam> getAllExams() {
        return repository.findAll();
    }

    @Override
    public AbroadExam getExamById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Exam");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    @Override
    public AbroadExam updateExam(Long id, AbroadExam exam, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Exam");
        }

        AbroadExam existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        existing.setName(exam.getName() != null ? exam.getName() : existing.getName());
        existing.setContactNumber(exam.getContactNumber() != null ? exam.getContactNumber() : existing.getContactNumber());
        existing.setExamName(exam.getExamName() != null ? exam.getExamName() : existing.getExamName());

        return repository.save(existing);
    }

    @Override
    public void deleteExam(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Exam");
        }

        AbroadExam exam = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        repository.delete(exam);
    }
}