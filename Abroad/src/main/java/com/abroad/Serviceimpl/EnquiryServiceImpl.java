package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadEnquiry;
import com.abroad.Repository.EnquiryRepository;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EnquiryServiceImpl implements com.abroad.Service.EnquiryService {
    @Autowired
    private EnquiryRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadEnquiry createEnquiry(AbroadEnquiry abroadEnquiry, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Enquiry");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        if (image != null && !image.isEmpty()) {
            abroadEnquiry.setPhotoUrl(image.getOriginalFilename());
        }

        abroadEnquiry.setCreatedByEmail(email);
        abroadEnquiry.setRole(role);
        abroadEnquiry.setBranchCode(branchCode);

        return repository.save(abroadEnquiry);
    }

    @Override
    public List<AbroadEnquiry> getAllEnquiries(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Enquiries");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public AbroadEnquiry getEnquiryById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Enquiry");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enquiry not found"));
    }

    @Override
    public AbroadEnquiry updateEnquiry(Long id, AbroadEnquiry abroadEnquiry, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Enquiry");
        }

        AbroadEnquiry existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enquiry not found"));

        existing.setName(abroadEnquiry.getName() != null ? abroadEnquiry.getName() : existing.getName());
        existing.setPhone_no(abroadEnquiry.getPhone_no() != null ? abroadEnquiry.getPhone_no() : existing.getPhone_no());
        existing.setEmail(abroadEnquiry.getEmail() != null ? abroadEnquiry.getEmail() : existing.getEmail());
        existing.setEnquiry_date(abroadEnquiry.getEnquiry_date() != null ? abroadEnquiry.getEnquiry_date() : existing.getEnquiry_date());
        existing.setAddress(abroadEnquiry.getAddress() != null ? abroadEnquiry.getAddress() : existing.getAddress());

        if (image != null && !image.isEmpty()) {
            existing.setPhotoUrl(image.getOriginalFilename());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteEnquiry(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Enquiry");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enquiry not found"));

        repository.deleteById(id);
    }
}
