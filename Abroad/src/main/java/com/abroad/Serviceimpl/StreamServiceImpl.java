package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCollege;
import com.abroad.Entity.AbroadStream;
import com.abroad.Entity.AbroadUniversity;
import com.abroad.Repository.CollegeRepository;
import com.abroad.Repository.StreamRepository;
import com.abroad.Repository.UniversityRepository;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import com.abroad.Service.StreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamServiceImpl implements StreamService {

    @Autowired
    private StreamRepository repository;

    @Autowired
    private CollegeRepository universityRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadStream createStream(AbroadStream abroadStream, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Stream");
        }

//        String branchCode = permissionService.fetchBranchCode(role, email);
//        AbroadCollege university = universityRepository.findById(collegeId)
//                .orElseThrow(() -> new RuntimeException("College not found"));

        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = s3Service.uploadImage(image);
                abroadStream.setImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload stream image", e);
            }
        }

        abroadStream.setCreatedByEmail(email);
        abroadStream.setRole(role);
//        abroadStream.setBranchCode(branchCode);
//        abroadStream.setAbroadCollege(university);

        return repository.save(abroadStream);
    }

    @Override
    public List<AbroadStream> getAllStreams() {
//        if (!permissionService.hasPermission(role, email, "GET")) {
//            throw new AccessDeniedException("No permission to view Streams");
//        }

        return repository.findAll();

//        return (collegeId != null)
//                ? repository.findAllByBranchCodeAndCollegeId( collegeId)
//                : repository.findAll();
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
            try {
                if (existing.getImage() != null) {
                    s3Service.deleteImage(existing.getImage());
                }
                String newImageUrl = s3Service.uploadImage(image);
                existing.setImage(newImageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to update stream image", e);
            }
        }

        return repository.save(existing);
    }

    @Override
    public void deleteStream(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Stream");
        }

        AbroadStream existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        if (existing.getImage() != null) {
            s3Service.deleteImage(existing.getImage());
        }

        repository.deleteById(id);
    }
}

