package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadContinent;
import com.abroad.Repository.ContinentRepository;
import com.abroad.Service.ContinentService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ContinentServiceImpl implements ContinentService {
    @Autowired
    private ContinentRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;


    @Override
    public AbroadContinent createContinent(AbroadContinent abroadContinent, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Continent");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        // Handle image upload
        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3Service.uploadImage(image);
                abroadContinent.setImage(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to S3", e);
        }

        abroadContinent.setCreatedByEmail(email);
        abroadContinent.setRole(role);
//    abroadContinent.setBranchCode(branchCode);

        return repository.save(abroadContinent);
    }

    @Override
    public List<AbroadContinent> getAllContinents(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Continents");
        }

////         branchCode = permissionService.fetchBranchCode(role, email);
//        return repository.findAllByBranchCode(branchCode);
        return repository.findAll();
    }

    @Override
    public AbroadContinent getContinentById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Continent");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
    }

    @Override
    public AbroadContinent updateContinent(Long id, AbroadContinent abroadContinent, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Continent");
        }

        AbroadContinent existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        existing.setContinentname(abroadContinent.getContinentname() != null ? abroadContinent.getContinentname() : existing.getContinentname());

        // Handle image update
        if (image != null && !image.isEmpty()) {
            try {
                if (existing.getImage() != null) {
                    s3Service.deleteImage(existing.getImage());
                }
                String newImageUrl = s3Service.uploadImage(image);
                existing.setImage(newImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to handle image update", e);
            }
        }

        return repository.save(existing);
    }

    @Override
    public void deleteContinent(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Continent");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        repository.deleteById(id);
    }
}
