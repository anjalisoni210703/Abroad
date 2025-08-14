package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadRegisterForm;
import com.abroad.Repository.AbroadRegisterFormRepository;
import com.abroad.Service.AbroadRegisterFormService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadRegisterFormServiceImpl implements AbroadRegisterFormService {

    @Autowired
    private AbroadRegisterFormRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadRegisterForm createRegisterForm(AbroadRegisterForm form) {
//        if (!permissionService.hasPermission(role, email, "POST")) {
//            throw new AccessDeniedException("No permission to create RegisterForm");
//        }

//        String branchCode = permissionService.fetchBranchCode(role, email);
//        form.setCreatedByEmail(email);
//        form.setRole(role);
//        form.setBranchCode(branchCode);

        return repository.save(form);
    }

    @Override
    public List<AbroadRegisterForm> getAllRegisterForms(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view RegisterForms");
        }

        return repository.findAll();
    }

    @Override
    public AbroadRegisterForm getRegisterFormById(int id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view RegisterForm");
        }

        return repository.findById((long) id)
                .orElseThrow(() -> new RuntimeException("RegisterForm not found"));
    }

    @Override
    public AbroadRegisterForm updateRegisterForm(int id, AbroadRegisterForm form, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update RegisterForm");
        }

        AbroadRegisterForm existing = repository.findById((long) id)
                .orElseThrow(() -> new RuntimeException("RegisterForm not found"));

        existing.setName(form.getName() != null ? form.getName() : existing.getName());
        existing.setEmail(form.getEmail() != null ? form.getEmail() : existing.getEmail());
        existing.setMobileNumber(form.getMobileNumber() != null ? form.getMobileNumber() : existing.getMobileNumber());
        existing.setStream(form.getStream() != null ? form.getStream() : existing.getStream());
        existing.setCourseName(form.getCourseName() != null ? form.getCourseName() : existing.getCourseName());
        existing.setLocation(form.getLocation() != null ? form.getLocation() : existing.getLocation());
        existing.setAmount(form.getAmount() != null ? form.getAmount() : existing.getAmount());

        return repository.save(existing);
    }

    @Override
    public void deleteRegisterForm(int id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete RegisterForm");
        }

        AbroadRegisterForm existing = repository.findById((long) id)
                .orElseThrow(() -> new RuntimeException("RegisterForm not found"));

        repository.deleteById((long) id);
    }
}
