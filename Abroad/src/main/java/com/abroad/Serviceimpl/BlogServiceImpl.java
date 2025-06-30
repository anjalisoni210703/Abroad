package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadBlog;
import com.abroad.Repository.BlogRepository;
import com.abroad.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.abroad.Service.PermissionService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadBlog createBlog(AbroadBlog abroadBlog, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Blog");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        abroadBlog.setCreatedByEmail(email);
        abroadBlog.setRole(role);
        abroadBlog.setBranchCode(branchCode);

        if (image != null && !image.isEmpty()) {
            abroadBlog.setImage(image.getOriginalFilename());
        }

        return repository.save(abroadBlog);
    }

    @Override
    public List<AbroadBlog> getAllBlogsByBranchCode(String branchCode, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Blogs by branch code");
        }
        return repository.findAllByBranchCode(branchCode);
    }


    @Override
    public AbroadBlog getBlogById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Blog");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    @Override
    public AbroadBlog updateBlog(Long id, AbroadBlog abroadBlog, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Blog");
        }

        AbroadBlog existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        existing.setBlog(abroadBlog.getBlog() != null ? abroadBlog.getBlog() : existing.getBlog());

        if (image != null && !image.isEmpty()) {
            existing.setImage(image.getOriginalFilename());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteBlog(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Blog");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        repository.deleteById(id);
    }
}
