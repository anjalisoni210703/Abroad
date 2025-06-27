package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadContact;
import com.abroad.Repository.ContactRepository;
import com.abroad.Service.ContactService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadContact createContact(AbroadContact abroadContact, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Contact");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        abroadContact.setCreatedByEmail(email);
        abroadContact.setRole(role);
        abroadContact.setBranchCode(branchCode);

        return repository.save(abroadContact);
    }

    @Override
    public List<AbroadContact> getAllContacts(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Contacts");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public AbroadContact getContactById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Contact");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    public AbroadContact updateContact(Long id, AbroadContact abroadContact, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Contact");
        }

        AbroadContact existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        existing.setName(abroadContact.getName() != null ? abroadContact.getName() : existing.getName());
        existing.setEmail(abroadContact.getEmail() != null ? abroadContact.getEmail() : existing.getEmail());
        existing.setMobno(abroadContact.getMobno() != null ? abroadContact.getMobno() : existing.getMobno());
        existing.setPincode(abroadContact.getPincode() != null ? abroadContact.getPincode() : existing.getPincode());

        return repository.save(existing);
    }

    @Override
    public void deleteContact(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Contact");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        repository.deleteById(id);
    }
}
