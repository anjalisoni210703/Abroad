package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadBlog;
import com.abroad.Repository.BlogRepository;
import com.abroad.Service.BlogService;
import com.abroad.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private S3Service s3Service;


    @Override
    public AbroadBlog createBlog(AbroadBlog abroadBlog, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Blog");
        }

//        String branchCode = permissionService.fetchBranchCode(role, email);

        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3Service.uploadImage(image);
                abroadBlog.setImage(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload blog image", e);
        }

        abroadBlog.setCreatedByEmail(email);
        abroadBlog.setRole(role);
//        abroadBlog.setBranchCode(branchCode);

        return repository.save(abroadBlog);
    }

    @Override
    public List<AbroadBlog> getAllBlogsByBranchCode() {

        return repository.findAll();
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

        existing.setDescription(abroadBlog.getDescription() != null ? abroadBlog.getDescription() : existing.getDescription());
        existing.setTitle(abroadBlog.getTitle() != null ? abroadBlog.getTitle() : existing.getTitle());
        existing.setCategory(abroadBlog.getCategory() != null ? abroadBlog.getCategory() : existing.getCategory());

        try {
            if (image != null && !image.isEmpty()) {
                if (existing.getImage() != null) {
                    s3Service.deleteImage(existing.getImage());
                }

                String newImageUrl = s3Service.uploadImage(image);
                existing.setImage(newImageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update blog image", e);
        }

        return repository.save(existing);
    }

    @Override
    public void deleteBlog(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Blog");
        }

        AbroadBlog existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));

        if (existing.getImage() != null) {
            s3Service.deleteImage(existing.getImage());
        }

        repository.deleteById(id);
    }
}
