package com.abroad.serviceimpl;

import com.abroad.entity.Contact;
import com.abroad.repository.ContactRepository;
import com.abroad.service.ContactService;
import com.abroad.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Contact createContact(Contact contact, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Contact");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        contact.setCreatedByEmail(email);
        contact.setRole(role);
        contact.setBranchCode(branchCode);

        return repository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Contacts");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Contact getContactById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Contact");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    public Contact updateContact(Long id, Contact contact, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Contact");
        }

        Contact existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        existing.setName(contact.getName() != null ? contact.getName() : existing.getName());
        existing.setEmail(contact.getEmail() != null ? contact.getEmail() : existing.getEmail());
        existing.setMobno(contact.getMobno() != null ? contact.getMobno() : existing.getMobno());
        existing.setPincode(contact.getPincode() != null ? contact.getPincode() : existing.getPincode());

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
