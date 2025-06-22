package com.abroad.serviceimpl;

import com.abroad.entity.Blog;
import com.abroad.repository.BlogRepository;
import com.abroad.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.abroad.entity.Blog;
import com.abroad.repository.BlogRepository;
import com.abroad.service.BlogService;
import com.abroad.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Blog createBlog(Blog blog, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Blog");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        blog.setCreatedByEmail(email);
        blog.setRole(role);
        blog.setBranchCode(branchCode);

        if (image != null && !image.isEmpty()) {
            blog.setImage(image.getOriginalFilename());
        }

        return repository.save(blog);
    }

    @Override
    public List<Blog> getAllBlogs(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Blogs");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Blog getBlogById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Blog");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    @Override
    public Blog updateBlog(Long id, Blog blog, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Blog");
        }

        Blog existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        existing.setBlog(blog.getBlog() != null ? blog.getBlog() : existing.getBlog());

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
