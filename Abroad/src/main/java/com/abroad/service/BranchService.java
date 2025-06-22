package com.abroad.service;

import com.abroad.entity.Branch;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BranchService {
    Branch createBranch(Branch branch, MultipartFile image, String role, String email);
    List<Branch> getAllBranches(String role, String email);
    Branch getBranchById(Long id, String role, String email);
    Branch updateBranch(Long id, Branch branch, MultipartFile image, String role, String email);
    void deleteBranch(Long id, String role, String email);
}
