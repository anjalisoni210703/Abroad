package com.abroad.serviceimpl;

import com.abroad.entity.Footer;
import com.abroad.repository.FooterRepository;
import com.abroad.service.FooterService;
import com.abroad.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FooterServiceImpl implements FooterService {
    @Autowired
    private FooterRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Footer createFooter(Footer footer, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Footer");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        if (image != null && !image.isEmpty()) {
            footer.setImage(image.getOriginalFilename());
        }

        footer.setCreatedByEmail(email);
        footer.setRole(role);
        footer.setBranchCode(branchCode);

        return repository.save(footer);
    }

    @Override
    public List<Footer> getAllFooters(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Footers");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Footer getFooterById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Footer");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found"));
    }

    @Override
    public Footer updateFooter(Long id, Footer footer, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Footer");
        }

        Footer existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found"));

        existing.setTitle(footer.getTitle() != null ? footer.getTitle() : existing.getTitle());
        existing.setFooterColor(footer.getFooterColor() != null ? footer.getFooterColor() : existing.getFooterColor());
        existing.setInstagramLink(footer.getInstagramLink() != null ? footer.getInstagramLink() : existing.getInstagramLink());
        existing.setFacebookLink(footer.getFacebookLink() != null ? footer.getFacebookLink() : existing.getFacebookLink());
        existing.setTwitterLink(footer.getTwitterLink() != null ? footer.getTwitterLink() : existing.getTwitterLink());
        existing.setYoutubeLink(footer.getYoutubeLink() != null ? footer.getYoutubeLink() : existing.getYoutubeLink());
        existing.setWhatsappLink(footer.getWhatsappLink() != null ? footer.getWhatsappLink() : existing.getWhatsappLink());
        existing.setEmail(footer.getEmail() != null ? footer.getEmail() : existing.getEmail());
        existing.setMobileNumber(footer.getMobileNumber() != null ? footer.getMobileNumber() : existing.getMobileNumber());
        existing.setAddress(footer.getAddress() != null ? footer.getAddress() : existing.getAddress());

        if (image != null && !image.isEmpty()) {
            existing.setImage(image.getOriginalFilename());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteFooter(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Footer");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found"));

        repository.deleteById(id);
    }
}
