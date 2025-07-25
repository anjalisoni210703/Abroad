package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadCollege;
import com.abroad.Entity.AbroadCourse;
import com.abroad.Entity.AbroadStream;
import com.abroad.Repository.CollegeRepository;
import com.abroad.Repository.CourseRepository;
import com.abroad.Repository.StreamRepository;
import com.abroad.Service.CourseService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository repository;

    @Autowired
    private CollegeRepository streamRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadCourse createCourse(AbroadCourse abroadCourse, MultipartFile image, String role, String email, Long collegeId) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Course");
        }

        AbroadCollege stream = streamRepository.findById(collegeId)
                .orElseThrow(() -> new RuntimeException("Stream not found"));

        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3Service.uploadImage(image);
                abroadCourse.setImage(imageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload course image", e);
        }

        abroadCourse.setAbroadCollege(stream);
        abroadCourse.setCreatedByEmail(email);
        abroadCourse.setRole(role);
        return repository.save(abroadCourse);
    }

    @Override
    public List<AbroadCourse> getAllCourses(String role, String email, Long collegeId) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view Courses");
        }

        if (collegeId != null) {
            return repository.findAllByBranchCodeAndStreamId( collegeId);
        } else {
            return repository.findAll();
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

        // Field updates
        existing.setCourseName(abroadCourse.getCourseName() != null ? abroadCourse.getCourseName() : existing.getCourseName());
        existing.setTutionFees(abroadCourse.getTutionFees() != null ? abroadCourse.getTutionFees() : existing.getTutionFees());
        existing.setApplicationFees(abroadCourse.getApplicationFees() != null ? abroadCourse.getApplicationFees() : existing.getApplicationFees());
        existing.setDescription(abroadCourse.getDescription() != null ? abroadCourse.getDescription() : existing.getDescription());
        existing.setDate(abroadCourse.getDate() != null ? abroadCourse.getDate() : existing.getDate());
        existing.setDuration(abroadCourse.getDuration() != null ? abroadCourse.getDuration() : existing.getDuration());
        existing.setInstituteRank(abroadCourse.getInstituteRank() != null ? abroadCourse.getInstituteRank() : existing.getInstituteRank());
        existing.setIntake(abroadCourse.getIntake() !=null ? abroadCourse.getIntake():existing.getIntake());
        existing.setWebsiteLink(abroadCourse.getWebsiteLink()!=null?abroadCourse.getWebsiteLink():existing.getWebsiteLink());
        existing.setAcademicRequirements(abroadCourse.getAcademicRequirements()!=null?abroadCourse.getAcademicRequirements():existing.getAcademicRequirements());
        existing.setEnglishExamRequirements(abroadCourse.getEnglishExamRequirements()!=null?abroadCourse.getEnglishExamRequirements():existing.getEnglishExamRequirements());
        existing.setExamScore(abroadCourse.getExamScore()!=null?abroadCourse.getExamScore():existing.getExamScore());
        existing.setAdditionalRequirements(abroadCourse.getAdditionalRequirements()!=null?abroadCourse.getAdditionalRequirements():existing.getAdditionalRequirements());
        existing.setCity(abroadCourse.getCity()!=null?abroadCourse.getCity():existing.getCity());
        existing.setLocation(abroadCourse.getLocation()!=null?abroadCourse.getLocation():existing.getLocation());
        existing.setCourseDetials(abroadCourse.getCourseDetials()!=null?abroadCourse.getCourseDetials():existing.getCourseDetials());
        existing.setScholarship(abroadCourse.getScholarship()!=null?abroadCourse.getScholarship():existing.getScholarship());
        existing.setHostel(abroadCourse.getHostel()!=null?abroadCourse.getHostel():existing.getHostel());
        existing.setHostelFees(abroadCourse.getHostelFees()!=null?abroadCourse.getHostelFees():existing.getHostelFees());
        existing.setContractType(abroadCourse.getContractType()!=null?abroadCourse.getContractType():existing.getContractType());
        existing.setExamType(abroadCourse.getExamType()!=null?abroadCourse.getExamType():existing.getExamType());
        existing.setStreamName(abroadCourse.getStreamName()!=null?abroadCourse.getStreamName():existing.getStreamName());
        existing.setFeesINR(abroadCourse.getFeesINR()!=null?abroadCourse.getFeesINR():existing.getFeesINR());
        existing.setTutionFeesINR(abroadCourse.getTutionFeesINR()!=null?abroadCourse.getTutionFeesINR():existing.getTutionFeesINR());


        try {
            if (image != null && !image.isEmpty()) {
                if (existing.getImage() != null) {
                    s3Service.deleteImage(existing.getImage());
                }
                String newImageUrl = s3Service.uploadImage(image);
                existing.setImage(newImageUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update course image", e);
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

//    @Override
//    public List<AbroadCourse> filterCourses(List<Long> streamIds,
//                                            List<Long> collegeIds,
//                                            List<Long> universityIds,
//                                            List<Long> cityIds,
//                                            List<Long> stateIds,
//                                            List<Long> countryIds,
//                                            List<Long> continentIds,
//                                            String role,
//                                            String email) {
//
//        if (!permissionService.hasPermission(role, email, "GET")) {
//            throw new AccessDeniedException("No permission to filter Courses");
//        }
//
//        streamIds = (streamIds != null && !streamIds.isEmpty()) ? streamIds : null;
//        collegeIds = (collegeIds != null && !collegeIds.isEmpty()) ? collegeIds : null;
//        universityIds = (universityIds != null && !universityIds.isEmpty()) ? universityIds : null;
//        cityIds = (cityIds != null && !cityIds.isEmpty()) ? cityIds : null;
//        stateIds = (stateIds != null && !stateIds.isEmpty()) ? stateIds : null;
//        countryIds = (countryIds != null && !countryIds.isEmpty()) ? countryIds : null;
//        continentIds = (continentIds != null && !continentIds.isEmpty()) ? continentIds : null;
//
//        return repository.filterCourses(streamIds, collegeIds, universityIds, cityIds, stateIds, countryIds, continentIds);
//    }


}
