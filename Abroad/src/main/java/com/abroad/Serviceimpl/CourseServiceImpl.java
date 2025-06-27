package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCourse;
import com.abroad.Entity.AbroadStream;
import com.abroad.Repository.CourseRepository;
import com.abroad.Repository.StreamRepository;
import com.abroad.Service.CourseService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository repository;

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadCourse createCourse(AbroadCourse abroadCourse, MultipartFile image, String role, String email, Long streamId) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Course");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);

        AbroadStream stream = streamRepository.findById(streamId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        if (image != null && !image.isEmpty()) {
            abroadCourse.setThumbnail(image.getOriginalFilename());
        }

        abroadCourse.setAbroadStream(stream);
        abroadCourse.setCreatedByEmail(email);
        abroadCourse.setRole(role);
        abroadCourse.setBranchCode(branchCode);

        return repository.save(abroadCourse);
    }

    @Override
    public List<AbroadCourse> getAllCourses(String role, String email, String branchCode, Long streamId) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Courses");
        }

        if (streamId != null) {
            return repository.findAllByBranchCodeAndStreamId(branchCode, streamId);
        } else {
            return repository.findAllByBranchCode(branchCode);
        }
    }

    @Override
    public AbroadCourse getCourseById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Course");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }


    @Override
    public AbroadCourse updateCourse(Long id, AbroadCourse abroadCourse, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Course");
        }

        AbroadCourse existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existing.setCourseName(abroadCourse.getCourseName() != null ? abroadCourse.getCourseName() : existing.getCourseName());
        existing.setPrice(abroadCourse.getPrice() != null ? abroadCourse.getPrice() : existing.getPrice());
        existing.setDiscountPrice(abroadCourse.getDiscountPrice() != null ? abroadCourse.getDiscountPrice() : existing.getDiscountPrice());
        existing.setDescription(abroadCourse.getDescription() != null ? abroadCourse.getDescription() : existing.getDescription());
        existing.setDate(abroadCourse.getDate() != null ? abroadCourse.getDate() : existing.getDate());
        existing.setValidity(abroadCourse.getValidity() != null ? abroadCourse.getValidity() : existing.getValidity());
        existing.setCategoryName(abroadCourse.getCategoryName() != null ? abroadCourse.getCategoryName() : existing.getCategoryName());

        if (image != null && !image.isEmpty()) {
            existing.setThumbnail(image.getOriginalFilename());
        }

        return repository.save(existing);
    }

    @Override
    public void deleteCourse(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete Course");
        }

        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        repository.deleteById(id);
    }

}
