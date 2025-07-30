package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadConductBy;
import com.abroad.Repository.AbroadConductByRepository;
import com.abroad.Service.AbroadConductByService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadConductByServiceImpl implements AbroadConductByService {

    @Autowired
    private AbroadConductByRepository conductByRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadConductBy addConductBy(String role, String email, AbroadConductBy conductBy) {
        if (!permissionService.hasPermission(role, email, "Post")) {
            throw new RuntimeException("Access Denied");
        }
        conductBy.setCreatedByEmail(email);
        conductBy.setRole(role);
        return conductByRepository.save(conductBy);
    }

    @Override
    public AbroadConductBy getConductByById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "Get")) {
            throw new RuntimeException("Access Denied");
        }
        return conductByRepository.findById(id).get();

    }

    @Override
    public List<AbroadConductBy> getAllConductBy(String role, String email) {
        if (!permissionService.hasPermission(role, email, "Get")) {
            throw new RuntimeException("Access Denied");
        }
        return conductByRepository.findAll();
    }

    @Override
    public AbroadConductBy updateConductBy(Long id, String role, String email, AbroadConductBy conductBy) {
        if (!permissionService.hasPermission(role, email, "Put")) {
            throw new RuntimeException("Access Denied");
        }
        AbroadConductBy existing = conductByRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ConductBy not found"));

        existing.setRole(role);
        existing.setCreatedByEmail(email);
        existing.setConductBy(conductBy.getConductBy() != null ? conductBy.getConductBy() : existing.getConductBy());

        return conductByRepository.save(existing);
    }

    @Override
    public Void deleteConductBy(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "Delete")) {
            throw new RuntimeException("Access Denied");
        }
        conductByRepository.deleteById(id);
        return null;
    }
}

