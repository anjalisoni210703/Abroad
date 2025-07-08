package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadContinent;
import com.abroad.Repository.ContinentRepository;
import com.abroad.Service.ContinentService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class ContinentServiceImpl implements ContinentService {
    @Autowired
    private ContinentRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadContinent createContinent(AbroadContinent abroadContinent, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Continent");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        abroadContinent.setCreatedByEmail(email);
        abroadContinent.setRole(role);
//        abroadContinent.setBranchCode(branchCode);

        return repository.save(abroadContinent);
    }

    @Override
    public List<AbroadContinent> getAllContinents(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Continents");
        }

////         branchCode = permissionService.fetchBranchCode(role, email);
//        return repository.findAllByBranchCode(branchCode);
        return repository.findAll();
    }

    @Override
    public AbroadContinent getContinentById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Continent");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
    }

    @Override
    public AbroadContinent updateContinent(Long id, AbroadContinent abroadContinent, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Continent");
        }

        AbroadContinent existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        existing.setContinentname(abroadContinent.getContinentname() != null ? abroadContinent.getContinentname() : existing.getContinentname());

        return repository.save(existing);
    }

    @Override
    public void deleteContinent(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Continent");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        repository.deleteById(id);
    }
}
