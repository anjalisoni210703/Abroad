package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadExamPreparation;
import com.abroad.Repository.AbroadExamPreparationRepository;
import com.abroad.Service.AbroadExamPreparationService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadExamPreparationServiceImpl implements AbroadExamPreparationService {

    @Autowired
    private AbroadExamPreparationRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadExamPreparation createExam(AbroadExamPreparation exam, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Exam");
        }

        exam.setCreatedByEmail(email);
        exam.setRole(role);
        return repository.save(exam);
    }

    @Override
    public List<AbroadExamPreparation> getAllExams() {
        return repository.findAll();
    }

    @Override
    public AbroadExamPreparation getExamById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Exam");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    @Override
    public AbroadExamPreparation updateExam(Long id, AbroadExamPreparation exam, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Exam");
        }

        AbroadExamPreparation existing = repository.findById(id)
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

        AbroadExamPreparation exam = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        repository.delete(exam);
    }
}