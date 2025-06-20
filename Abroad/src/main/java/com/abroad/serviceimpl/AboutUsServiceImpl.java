package com.abroad.serviceimpl;

import com.abroad.entity.AboutUs;
import com.abroad.repository.AboutUsRepository;
import com.abroad.service.AboutUsService;
import com.abroad.service.PermissionService;
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
        public AboutUs createAboutUs(AboutUs aboutUs, MultipartFile image, String role, String email) {
            if (!permissionService.hasPermission(role, email, "POST")) {
                throw new AccessDeniedException("No permission to create AboutUs");
            }

            String branchCode = permissionService.fetchBranchCode(role, email);
            String imageName = image != null ? image.getOriginalFilename() : null;

            aboutUs.setAboutUsImage(imageName);
            aboutUs.setCreatedByEmail(email);
            aboutUs.setRole(role);
            aboutUs.setBranchCode(branchCode);

            return repository.save(aboutUs);
        }

    @Override
    public List<AboutUs> getAllAboutUs(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view AboutUs");
        }
        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public AboutUs getAboutUsById(int id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view AboutUs");
        }
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs not found"));
    }

    @Override
    public AboutUs updateAboutUs(int id, AboutUs aboutUs, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update AboutUs");
        }

        AboutUs existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("AboutUs not found"));

        existing.setAboutUsTitle(aboutUs.getAboutUsTitle() != null ? aboutUs.getAboutUsTitle() : existing.getAboutUsTitle());
        existing.setAboutUsDescription(aboutUs.getAboutUsDescription() != null ? aboutUs.getAboutUsDescription() : existing.getAboutUsDescription());

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
