package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadBecomePartner;
import com.abroad.Exception.ResourceNotFoundException;
import com.abroad.Repository.AbroadBecomePartnerRepository;
import com.abroad.Service.AbroadBecomePartnerService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadBecomePartnerServiceImpl implements AbroadBecomePartnerService {

    @Autowired
    private AbroadBecomePartnerRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadBecomePartner createPartner(AbroadBecomePartner partner, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create partner");
        }

//        String branchCode = permissionService.fetchBranchCode(role, email);
        partner.setCreatedByEmail(email);
        partner.setRole(role);
//        partner.setBranchCode(branchCode);

        return repository.save(partner);
    }

    @Override
    public List<AbroadBecomePartner> getAllPartners(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view partners");
        }
//        return repository.findAllByBranchCode(branchCode);
        return repository.findAll();
    }

    @Override
    public AbroadBecomePartner getPartnerById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view partner");
        }

        AbroadBecomePartner partner = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

//        if (!partner.getBranchCode().equals(branchCode)) {
//            throw new AccessDeniedException("Access denied to this branch data");
//        }

        return partner;
    }

    @Override
    public AbroadBecomePartner updatePartner(Long id, AbroadBecomePartner updated, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update partner");
        }

        AbroadBecomePartner existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

        existing.setName(updated.getName() != null ? updated.getName() : existing.getName());
        existing.setBusinessName(updated.getBusinessName() != null ? updated.getBusinessName() : existing.getBusinessName());
        existing.setEmail(updated.getEmail() != null ? updated.getEmail() : existing.getEmail());
        existing.setContactNumber(updated.getContactNumber() != null ? updated.getContactNumber() : existing.getContactNumber());
        existing.setCity(updated.getCity() != null ? updated.getCity() : existing.getCity());

        return repository.save(existing);
    }

    @Override
    public void deletePartner(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete partner");
        }

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

        repository.deleteById(id);
    }
}