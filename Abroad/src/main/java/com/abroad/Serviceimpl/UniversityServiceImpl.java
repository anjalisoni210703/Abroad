package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadUniversity;
import com.abroad.Repository.CountryRepository;
import com.abroad.Repository.UniversityRepository;
import com.abroad.Service.PermissionService;
import com.abroad.Service.UniversityService;
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

    @Autowired
    private CountryRepository countryRepository;


    @Override
    public AbroadUniversity createUniversity(AbroadUniversity abroadUniversity, MultipartFile image, String role, String email, Long countryId) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create University");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        if (image != null && !image.isEmpty()) {
            abroadUniversity.setImage(image.getOriginalFilename());
        }

        abroadUniversity.setCreatedByEmail(email);
        abroadUniversity.setRole(role);
        abroadUniversity.setBranchCode(branchCode);
        abroadUniversity.setAbroadCountry(
                countryRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country not found"))
        );

        return repository.save(abroadUniversity);
    }

    @Override
    public List<AbroadUniversity> getAllUniversities(String role, String email, String branchCode, Long countryId) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Universities");
        }

        if (countryId != null) {
            return repository.findAllByBranchCodeAndCountry(branchCode, countryId);
        } else {
            return repository.findAllByBranchCode(branchCode);
        }
    }

    @Override
    public AbroadUniversity getUniversityById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view University");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));
    }

    @Override
    public AbroadUniversity updateUniversity(Long id, AbroadUniversity abroadUniversity, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update University");
        }

        AbroadUniversity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));

        existing.setUniversityName(abroadUniversity.getUniversityName() != null ? abroadUniversity.getUniversityName() : existing.getUniversityName());

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
