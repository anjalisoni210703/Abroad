package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCollege;
import com.abroad.Entity.AbroadStream;
import com.abroad.Repository.CollegeRepository;
import com.abroad.Repository.StreamRepository;
import com.abroad.Service.PermissionService;
import com.abroad.Service.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl  implements StreamService {
    @Autowired
    private StreamRepository repository;

    @Autowired
    private CollegeRepository collegeRepository;


    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadStream createStream(AbroadStream abroadStream, MultipartFile image, String role, String email, Long collegeId) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Stream");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        AbroadCollege college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new RuntimeException("College not found"));

        if (image != null && !image.isEmpty()) {
            abroadStream.setImage(image.getOriginalFilename());
        }

        abroadStream.setCreatedByEmail(email);
        abroadStream.setRole(role);
        abroadStream.setBranchCode(branchCode);
        abroadStream.setAbroadCollege(college);

        return repository.save(abroadStream);
    }

    @Override
    public List<AbroadStream> getAllStreams(String role, String email, String branchCode, Long collegeId) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Streams");
        }

        if (collegeId != null) {
            return repository.findAllByBranchCodeAndCollegeId(branchCode, collegeId);
        } else {
            return repository.findAllByBranchCode(branchCode);
        }
    }

    @Override
    public AbroadStream getStreamById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Stream");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found"));
    }

    @Override
    public AbroadStream updateStream(Long id, AbroadStream abroadStream, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Stream");
        }

        AbroadStream existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        existing.setName(abroadStream.getName() != null ? abroadStream.getName() : existing.getName());

        if (image != null && !image.isEmpty()) {
            existing.setImage(image.getOriginalFilename());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteStream(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Stream");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        repository.deleteById(id);
    }

}
