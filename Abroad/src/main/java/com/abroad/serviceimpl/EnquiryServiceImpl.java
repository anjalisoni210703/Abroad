package com.abroad.serviceimpl;

import com.abroad.dto.AddressDTO;
import com.abroad.dto.EnquiryDTO;
import com.abroad.dto.EnquiryFilterDTO;
import com.abroad.entity.Address;
import com.abroad.entity.Enquiry;
import com.abroad.repository.EnquiryRepository;
import com.abroad.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EnquiryServiceImpl implements com.abroad.service.EnquiryService {
    @Autowired
    private EnquiryRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Enquiry createEnquiry(Enquiry enquiry, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Enquiry");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        if (image != null && !image.isEmpty()) {
            enquiry.setPhotoUrl(image.getOriginalFilename());
        }

        enquiry.setCreatedByEmail(email);
        enquiry.setRole(role);
        enquiry.setBranchCode(branchCode);

        return repository.save(enquiry);
    }

    @Override
    public List<Enquiry> getAllEnquiries(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Enquiries");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Enquiry getEnquiryById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Enquiry");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enquiry not found"));
    }

    @Override
    public Enquiry updateEnquiry(Long id, Enquiry enquiry, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Enquiry");
        }

        Enquiry existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enquiry not found"));

        existing.setName(enquiry.getName() != null ? enquiry.getName() : existing.getName());
        existing.setPhone_no(enquiry.getPhone_no() != null ? enquiry.getPhone_no() : existing.getPhone_no());
        existing.setEmail(enquiry.getEmail() != null ? enquiry.getEmail() : existing.getEmail());
        existing.setBatch(enquiry.getBatch() != null ? enquiry.getBatch() : existing.getBatch());
        existing.setSourceby(enquiry.getSourceby() != null ? enquiry.getSourceby() : existing.getSourceby());
        existing.setConducts(enquiry.getConducts() != null ? enquiry.getConducts() : existing.getConducts());
        existing.setStatus(enquiry.getStatus() != null ? enquiry.getStatus() : existing.getStatus());
        existing.setEnquiry_date(enquiry.getEnquiry_date() != null ? enquiry.getEnquiry_date() : existing.getEnquiry_date());
        existing.setRemark(enquiry.getRemark() != null ? enquiry.getRemark() : existing.getRemark());
        existing.setDob(enquiry.getDob() != null ? enquiry.getDob() : existing.getDob());
        existing.setGender(enquiry.getGender() != null ? enquiry.getGender() : existing.getGender());
        existing.setMothertounge(enquiry.getMothertounge() != null ? enquiry.getMothertounge() : existing.getMothertounge());
        existing.setFatherprofession(enquiry.getFatherprofession() != null ? enquiry.getFatherprofession() : existing.getFatherprofession());
        existing.setEducationqualification(enquiry.getEducationqualification() != null ? enquiry.getEducationqualification() : existing.getEducationqualification());
        existing.setAnnualincome(enquiry.getAnnualincome() != null ? enquiry.getAnnualincome() : existing.getAnnualincome());
        existing.setAddress(enquiry.getAddress() != null ? enquiry.getAddress() : existing.getAddress());

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
