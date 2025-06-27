package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadFooter;
import com.abroad.Repository.FooterRepository;
import com.abroad.Service.FooterService;
import com.abroad.Service.PermissionService;
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
    public AbroadFooter createFooter(AbroadFooter abroadFooter, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Footer");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        if (image != null && !image.isEmpty()) {
            abroadFooter.setImage(image.getOriginalFilename());
        }

        abroadFooter.setCreatedByEmail(email);
        abroadFooter.setRole(role);
        abroadFooter.setBranchCode(branchCode);

        return repository.save(abroadFooter);
    }

    @Override
    public List<AbroadFooter> getAllFooters(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Footers");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public AbroadFooter getFooterById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Footer");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found"));
    }

    @Override
    public AbroadFooter updateFooter(Long id, AbroadFooter abroadFooter, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Footer");
        }

        AbroadFooter existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Footer not found"));

        existing.setTitle(abroadFooter.getTitle() != null ? abroadFooter.getTitle() : existing.getTitle());
        existing.setFooterColor(abroadFooter.getFooterColor() != null ? abroadFooter.getFooterColor() : existing.getFooterColor());
        existing.setInstagramLink(abroadFooter.getInstagramLink() != null ? abroadFooter.getInstagramLink() : existing.getInstagramLink());
        existing.setFacebookLink(abroadFooter.getFacebookLink() != null ? abroadFooter.getFacebookLink() : existing.getFacebookLink());
        existing.setTwitterLink(abroadFooter.getTwitterLink() != null ? abroadFooter.getTwitterLink() : existing.getTwitterLink());
        existing.setYoutubeLink(abroadFooter.getYoutubeLink() != null ? abroadFooter.getYoutubeLink() : existing.getYoutubeLink());
        existing.setWhatsappLink(abroadFooter.getWhatsappLink() != null ? abroadFooter.getWhatsappLink() : existing.getWhatsappLink());
        existing.setEmail(abroadFooter.getEmail() != null ? abroadFooter.getEmail() : existing.getEmail());
        existing.setMobileNumber(abroadFooter.getMobileNumber() != null ? abroadFooter.getMobileNumber() : existing.getMobileNumber());
        existing.setAddress(abroadFooter.getAddress() != null ? abroadFooter.getAddress() : existing.getAddress());

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
