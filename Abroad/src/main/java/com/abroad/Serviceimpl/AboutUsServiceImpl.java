package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadAboutUs;
import com.abroad.Repository.AboutUsRepository;
import com.abroad.Service.AboutUsService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsRepository repository;

    @Autowired
    private PermissionService permissionService;

        @Override
        public AbroadAboutUs createAboutUs(AbroadAboutUs abroadAboutUs, MultipartFile image, String role, String email) {
            if (!permissionService.hasPermission(role, email, "POST")) {
                throw new AccessDeniedException("No permission to create AboutUs");
            }

            String branchCode = permissionService.fetchBranchCode(role, email);
            String imageName = image != null ? image.getOriginalFilename() : null;

            abroadAboutUs.setAboutUsImage(imageName);
            abroadAboutUs.setCreatedByEmail(email);
            abroadAboutUs.setRole(role);
            abroadAboutUs.setBranchCode(branchCode);

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

        if (image != null && !image.isEmpty()) {
            existing.setAboutUsImage(image.getOriginalFilename());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteAboutUs(int id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete AboutUs");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs not found"));

        repository.deleteById(id);
    }
}
