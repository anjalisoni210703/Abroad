package com.abroad.serviceimpl;
import com.abroad.entity.Address;
import com.abroad.repository.AddressRepository;
import com.abroad.service.AddressService;
import com.abroad.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Address createAddress(Address address, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Address");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        address.setCreatedByEmail(email);
        address.setRole(role);
        address.setBranchCode(branchCode);

        return repository.save(address);
    }

    @Override
    public List<Address> getAllAddresses(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Address");
        }
        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Address getAddressById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Address");
        }
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    @Override
    public Address updateAddress(Long id, Address address, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Address");
        }

        Address existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        existing.setAddress(address.getAddress() != null ? address.getAddress() : existing.getAddress());
        existing.setLandmark(address.getLandmark() != null ? address.getLandmark() : existing.getLandmark());
        existing.setState(address.getState() != null ? address.getState() : existing.getState());
        existing.setDistrict(address.getDistrict() != null ? address.getDistrict() : existing.getDistrict());

        return repository.save(existing);
    }

    @Override
    public void deleteAddress(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Address");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        repository.deleteById(id);
    }
}
