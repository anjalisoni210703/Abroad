package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadUniversity;
import com.abroad.Repository.CityRepository;
import com.abroad.Repository.CountryRepository;
import com.abroad.Repository.UniversityRepository;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import com.abroad.Service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CityRepository countryRepository;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadUniversity createUniversity(AbroadUniversity abroadUniversity, MultipartFile image, String role, String email, Long cityId) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create University");
        }

//        String branchCode = permissionService.fetchBranchCode(role, email);

        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadImage(image);
                abroadUniversity.setImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        abroadUniversity.setCreatedByEmail(email);
        abroadUniversity.setRole(role);
//        abroadUniversity.setBranchCode(branchCode);
        abroadUniversity.setAbroadCity(
                countryRepository.findById(cityId).orElseThrow(() -> new RuntimeException("Country not found"))
        );

        return repository.save(abroadUniversity);
    }

    @Override
    public List<AbroadUniversity> getAllUniversities(String role, String email, Long cityId) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Universities");
        }

        if (cityId != null) {
            return repository.findAllByBranchCodeAndCountry( cityId);
        } else {
            return repository.findAll();
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
            try {
                if (existing.getImage() != null) {
                    s3Service.deleteImage(existing.getImage());
                }
                String newImageUrl = s3Service.uploadImage(image);
                existing.setImage(newImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Image upload/update failed", e);
            }
        }

        return repository.save(existing);
    }

    @Override
    public void deleteUniversity(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete University");
        }

        AbroadUniversity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("University not found"));

        if (existing.getImage() != null) {
            s3Service.deleteImage(existing.getImage());
        }

        repository.deleteById(id);
    }
}
