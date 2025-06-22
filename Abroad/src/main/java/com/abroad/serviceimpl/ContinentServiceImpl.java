package com.abroad.serviceimpl;

import com.abroad.entity.Continent;
import com.abroad.repository.ContinentRepository;
import com.abroad.service.ContinentService;
import com.abroad.service.PermissionService;
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
    public Continent createContinent(Continent continent, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Continent");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        continent.setCreatedByEmail(email);
        continent.setRole(role);
        continent.setBranchCode(branchCode);

        return repository.save(continent);
    }

    @Override
    public List<Continent> getAllContinents(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Continents");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Continent getContinentById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Continent");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
    }

    @Override
    public Continent updateContinent(Long id, Continent continent, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Continent");
        }

        Continent existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        existing.setContinentname(continent.getContinentname() != null ? continent.getContinentname() : existing.getContinentname());

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
