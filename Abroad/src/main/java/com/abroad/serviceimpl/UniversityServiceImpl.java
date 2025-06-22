package com.abroad.serviceimpl;

import com.abroad.entity.University;
import com.abroad.repository.UniversityRepository;
import com.abroad.service.PermissionService;
import com.abroad.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {
    @Autowired
    private UniversityRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public University createUniversity(University university, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create University");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        if (image != null && !image.isEmpty()) {
            university.setImage(image.getOriginalFilename());
        }

        university.setCreatedByEmail(email);
        university.setRole(role);
        university.setBranchCode(branchCode);

        return repository.save(university);
    }

    @Override
    public List<University> getAllUniversities(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Universities");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public University getUniversityById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view University");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    @Override
    public University updateUniversity(Long id, University university, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update University");
        }

        University existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));

        existing.setUniversityName(university.getUniversityName() != null ? university.getUniversityName() : existing.getUniversityName());

        if (image != null && !image.isEmpty()) {
            existing.setImage(image.getOriginalFilename());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteUniversity(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete University");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));

        repository.deleteById(id);
    }
}
