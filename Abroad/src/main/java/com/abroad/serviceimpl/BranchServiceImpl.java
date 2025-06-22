package com.abroad.serviceimpl;

import com.abroad.entity.Branch;
import com.abroad.repository.BranchRepository;
import com.abroad.service.BranchService;
import com.abroad.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    private BranchRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Branch createBranch(Branch branch, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Branch");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        branch.setCreatedByEmail(email);
        branch.setRole(role);
        branch.setBranchCode(branchCode);

        return repository.save(branch);
    }

    @Override
    public List<Branch> getAllBranches(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Branches");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Branch getBranchById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Branch");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
    }

    @Override
    public Branch updateBranch(Long id, Branch branch, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Branch");
        }

        Branch existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        existing.setCansGet(branch.isCansGet());
        existing.setCansPut(branch.isCansPut());
        existing.setCansPost(branch.isCansPost());
        existing.setCansDelete(branch.isCansDelete());

        return repository.save(existing);
    }

    @Override
    public void deleteBranch(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Branch");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        repository.deleteById(id);
    }
}
