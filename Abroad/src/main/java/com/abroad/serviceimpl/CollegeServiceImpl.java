package com.abroad.serviceimpl;

import com.abroad.entity.College;
import com.abroad.repository.CollegeRepository;
import com.abroad.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.abroad.service.PermissionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Autowired
    private CollegeRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public College createCollege(College college, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create College");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        college.setCreatedByEmail(email);
        college.setRole(role);
        college.setBranchCode(branchCode);

        return repository.save(college);
    }

    @Override
    public List<College> getAllColleges(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Colleges");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public College getCollegeById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view College");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));
    }

    @Override
    public College updateCollege(Long id, College college, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update College");
        }

        College existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("College not found"));

        existing.setCollegeName(college.getCollegeName() != null ? college.getCollegeName() : existing.getCollegeName());
        existing.setUniversity(college.getUniversity() != null ? college.getUniversity() : existing.getUniversity());
        existing.setStream(college.getStream() != null ? college.getStream() : existing.getStream());
        existing.setCourses(college.getCourses() != null ? college.getCourses() : existing.getCourses());

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
