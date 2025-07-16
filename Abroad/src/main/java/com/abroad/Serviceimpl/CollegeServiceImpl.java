package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCollege;
import com.abroad.Entity.AbroadUniversity;
import com.abroad.Repository.CollegeRepository;
import com.abroad.Repository.UniversityRepository;
import com.abroad.Service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abroad.Service.PermissionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    private CollegeRepository repository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadCollege createCollege(AbroadCollege abroadCollege, MultipartFile image, String role, String email, Long universityId) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create College");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        AbroadUniversity university = universityRepository.findById(universityId)
                .orElseThrow(() -> new RuntimeException("University not found"));

        abroadCollege.setCreatedByEmail(email);
        abroadCollege.setRole(role);
//        abroadCollege.setBranchCode(branchCode);
        abroadCollege.setAbroadUniversity(university);

        return repository.save(abroadCollege);
    }

    @Override
    public List<AbroadCollege> getAllColleges(String role, String email, Long universityId) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Colleges");
        }

        if (universityId != null) {
            return repository.findAllByBranchCodeAndUniversityId( universityId);
        } else {
            return repository.findAll();
        }
    }

    @Override
    public AbroadCollege getCollegeById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view College");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));
    }

    @Override
    public AbroadCollege updateCollege(Long id, AbroadCollege abroadCollege, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update College");
        }

        AbroadCollege existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));

        existing.setCollegeName(abroadCollege.getCollegeName() != null ? abroadCollege.getCollegeName() : existing.getCollegeName());
//        existing.setAbroadUniversity(abroadCollege.getAbroadUniversity() != null ? abroadCollege.getAbroadUniversity() : existing.getAbroadUniversity());

        return repository.save(existing);
    }

    @Override
    public void deleteCollege(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete College");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));

        repository.deleteById(id);
    }
}
