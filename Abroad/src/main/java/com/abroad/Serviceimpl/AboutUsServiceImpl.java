package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadAboutUs;
import com.abroad.Repository.AboutUsRepository;
import com.abroad.Service.AboutUsService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadAboutUs createAboutUs(AbroadAboutUs abroadAboutUs, MultipartFile image) {
//        if (!permissionService.hasPermission(role, email, "POST")) {
//            throw new AccessDeniedException("No permission to create AboutUs");
//        }
//
//        String branchCode = permissionService.fetchBranchCode(role, email);

//        Optional<AbroadAboutUs> existing = repository.findByBranchCode(branchCode);
//        if (existing.isPresent()) {
//            throw new RuntimeException("AboutUs already exists for this branch");
//        }

        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3Service.uploadImage(image);
                abroadAboutUs.setAboutUsImage(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to S3", e);
        }

//        abroadAboutUs.setCreatedByEmail(email);
//        abroadAboutUs.setRole(role);
//        abroadAboutUs.setBranchCode(branchCode);

        return repository.save(abroadAboutUs);
    }

    @Override
    public List<AbroadAboutUs> getAllAboutUs(String role, String email, String branchCode) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view AboutUs");
        }
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public AbroadAboutUs getAboutUsById(int id, String role, String email, String branchCode) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view AboutUs");
        }

        AbroadAboutUs aboutUs = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs not found"));

        if (!aboutUs.getBranchCode().equals(branchCode)) {
            throw new AccessDeniedException("Access denied to this branch data");
        }

        return aboutUs;
    }


    @Override
    public AbroadAboutUs updateAboutUs(int id, AbroadAboutUs abroadAboutUs, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update AboutUs");
        }

        AbroadAboutUs existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs not found"));

        existing.setAboutUsTitle(abroadAboutUs.getAboutUsTitle() != null ? abroadAboutUs.getAboutUsTitle() : existing.getAboutUsTitle());
        existing.setAboutUsDescription(abroadAboutUs.getAboutUsDescription() != null ? abroadAboutUs.getAboutUsDescription() : existing.getAboutUsDescription());

        // Handle image update
        if (image != null && !image.isEmpty()) {
            try {
                // Delete old image if exists
                if (existing.getAboutUsImage() != null) {
                    s3Service.deleteImage(existing.getAboutUsImage());
                }

                // Upload new image
                String newImageUrl = s3Service.uploadImage(image);
                existing.setAboutUsImage(newImageUrl);

            } catch (IOException e) {
                throw new RuntimeException("Failed to handle image update", e);
            }
        }

        return repository.save(existing);
    }


    @Override
    public void deleteAboutUs(int id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete AboutUs");
        }

        AbroadAboutUs existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs not found"));

        // Delete image from S3 if exists
        if (existing.getAboutUsImage() != null) {
            s3Service.deleteImage(existing.getAboutUsImage());
        }

        repository.deleteById(id);
    }
}
