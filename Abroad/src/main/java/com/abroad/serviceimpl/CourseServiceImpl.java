package com.abroad.serviceimpl;

import com.abroad.entity.Course;
import com.abroad.repository.CourseRepository;
import com.abroad.service.CourseService;
import com.abroad.service.PermissionService;
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
    private PermissionService permissionService;

    @Override
    public Course createCourse(Course course, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Course");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        if (image != null && !image.isEmpty()) {
            course.setThumbnail(image.getOriginalFilename());
        }
        course.setCreatedByEmail(email);
        course.setRole(role);
        course.setBranchCode(branchCode);

        return repository.save(course);
    }

    @Override
    public List<Course> getAllCourses(String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Courses");
        }

        String branchCode = permissionService.fetchBranchCode(role, email);
        return repository.findAllByBranchCode(branchCode);
    }

    @Override
    public Course getCourseById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Course");
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course updateCourse(Long id, Course course, MultipartFile image, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Course");
        }

        Course existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existing.setCourseName(course.getCourseName() != null ? course.getCourseName() : existing.getCourseName());
        existing.setPrice(course.getPrice() != null ? course.getPrice() : existing.getPrice());
        existing.setDiscountPrice(course.getDiscountPrice() != null ? course.getDiscountPrice() : existing.getDiscountPrice());
        existing.setDescription(course.getDescription() != null ? course.getDescription() : existing.getDescription());
        existing.setDate(course.getDate() != null ? course.getDate() : existing.getDate());
        existing.setValidity(course.getValidity() != null ? course.getValidity() : existing.getValidity());
        existing.setCategoryName(course.getCategoryName() != null ? course.getCategoryName() : existing.getCategoryName());
        existing.setCollege(course.getCollege() != null ? course.getCollege() : existing.getCollege());

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
