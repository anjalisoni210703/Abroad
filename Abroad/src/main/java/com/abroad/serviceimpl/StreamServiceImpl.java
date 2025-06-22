package com.abroad.serviceimpl;

import com.abroad.entity.Stream;
import com.abroad.repository.StreamRepository;
import com.abroad.service.PermissionService;
import com.abroad.service.StreamService;
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
    private PermissionService permissionService;

    @Override
    public Stream createStream(Stream stream, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Stream");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        if (image != null && !image.isEmpty()) {
            stream.setImage(image.getOriginalFilename());
        }

        stream.setCreatedByEmail(email);
        stream.setRole(role);
        stream.setBranchCode(branchCode);

        return repository.save(stream);
    }

    @Override
    public List<Stream> getAllStreams(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Streams");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Stream getStreamById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Stream");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found"));
    }

    @Override
    public Stream updateStream(Long id, Stream stream, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Stream");
        }

        Stream existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        existing.setName(stream.getName() != null ? stream.getName() : existing.getName());
        existing.setCourse(stream.getCourse() != null ? stream.getCourse() : existing.getCourse());

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
