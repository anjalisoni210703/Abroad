package com.abroad.Serviceimpl;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import com.abroad.Repository.AbroadAdmissionFormRepository;
import com.abroad.Service.AbroadAdmissionFormService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class AbroadAdmissionFormServiceImpl implements AbroadAdmissionFormService {

    @Autowired
    private AbroadAdmissionFormRepository repository;


    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadAdmissionForm createAdmissionForm(AbroadAdmissionForm form, String role, String email,String branchCode, MultipartFile sopFile,
                                                   MultipartFile lorsFile,
                                                   MultipartFile resumeFile,
                                                   MultipartFile testScoresFile,
                                                   MultipartFile passportCopyFile,
                                                   MultipartFile studentVisaFile,
                                                   MultipartFile passportPhotosFile) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to create Blog");
        }
        if(role.equals("superadmin")) {
            form.setBranchCode(branchCode);
        }else{
            String newBranchCode = permissionService.fetchBranchCode(role, email);
            form.setBranchCode(newBranchCode);
        }
        try {
            if (sopFile != null && !sopFile.isEmpty()) {
                String sopUrl = s3Service.uploadImage(sopFile);
                form.setSop(sopUrl);
            }

            if (lorsFile != null && !lorsFile.isEmpty()) {
                String lorsUrl = s3Service.uploadImage(lorsFile);
                form.setLors(lorsUrl);
            }

            if (resumeFile != null && !resumeFile.isEmpty()) {
                String resumeUrl = s3Service.uploadImage(resumeFile);
                form.setResume(resumeUrl);
            }

            if (testScoresFile != null && !testScoresFile.isEmpty()) {
                String testScoresUrl = s3Service.uploadImage(testScoresFile);
                form.setTestScores(testScoresUrl);
            }

            if (passportCopyFile != null && !passportCopyFile.isEmpty()) {
                String passportCopyUrl = s3Service.uploadImage(passportCopyFile);
                form.setPassportCopy(passportCopyUrl);
            }

            if (studentVisaFile != null && !studentVisaFile.isEmpty()) {
                String studentVisaUrl = s3Service.uploadImage(studentVisaFile);
                form.setStudentVisa(studentVisaUrl);
            }

            if (passportPhotosFile != null && !passportPhotosFile.isEmpty()) {
                String photosUrl = s3Service.uploadImage(passportPhotosFile);
                form.setPassportPhotos(photosUrl);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file(s)", e);
        }

        form.setCreatedDateTime(LocalDateTime.now());

        return repository.save(form);
    }



    @Override
    public AbroadAdmissionForm updateAdmissionForm(
            Long id,
            AbroadAdmissionForm form,
            String role,
            String email,
            MultipartFile sopFile,
            MultipartFile lorsFile,
            MultipartFile resumeFile,
            MultipartFile testScoresFile,
            MultipartFile passportCopyFile,
            MultipartFile studentVisaFile,
            MultipartFile passportPhotosFile) {

        // 1️⃣ Permission check
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Admission Form");
        }

        // 2️⃣ Fetch existing form
        AbroadAdmissionForm existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission form not found with id " + id));

        // 3️⃣ Update fields (you can selectively update only required fields)
        existing.setFullName(form.getFullName());
        existing.setEmail(form.getEmail());
        existing.setPhone(form.getPhone());
        existing.setAlternatePhone(form.getAlternatePhone());
        existing.setCountry(form.getCountry());
        existing.setUniversity(form.getUniversity());
        existing.setCourse(form.getCourse());
        existing.setStream(form.getStream());
        existing.setPassoutYear(form.getPassoutYear());
        existing.setStatus(form.getStatus());
        existing.setIntake(form.getIntake());
        existing.setNotes(form.getNotes());
        existing.setRole(role);             // update role
        existing.setCreatedByEmail(email);  // update email if needed
        existing.setBranchCode(form.getBranchCode());

        // 4️⃣ Upload documents to S3 if provided
        try {
            if (sopFile != null && !sopFile.isEmpty()) {
                String sopUrl = s3Service.uploadImage(sopFile);
                existing.setSop(sopUrl);
            }
            if (lorsFile != null && !lorsFile.isEmpty()) {
                String lorsUrl = s3Service.uploadImage(lorsFile);
                existing.setLors(lorsUrl);
            }
            if (resumeFile != null && !resumeFile.isEmpty()) {
                String resumeUrl = s3Service.uploadImage(resumeFile);
                existing.setResume(resumeUrl);
            }
            if (testScoresFile != null && !testScoresFile.isEmpty()) {
                String testScoresUrl = s3Service.uploadImage(testScoresFile);
                existing.setTestScores(testScoresUrl);
            }
            if (passportCopyFile != null && !passportCopyFile.isEmpty()) {
                String passportCopyUrl = s3Service.uploadImage(passportCopyFile);
                existing.setPassportCopy(passportCopyUrl);
            }
            if (studentVisaFile != null && !studentVisaFile.isEmpty()) {
                String studentVisaUrl = s3Service.uploadImage(studentVisaFile);
                existing.setStudentVisa(studentVisaUrl);
            }
            if (passportPhotosFile != null && !passportPhotosFile.isEmpty()) {
                String photosUrl = s3Service.uploadImage(passportPhotosFile);
                existing.setPassportPhotos(photosUrl);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload document(s) to S3", e);
        }

        // 5️⃣ Save updated form
        return repository.save(existing);
    }


    @Override
    public void deleteAdmissionForm(Long id,String role,String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to create Blog");
        }
        repository.deleteById(id);
    }

    @Override
    public AbroadAdmissionForm getAdmissionFormById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission form not found with id " + id));
    }

    @Override
    public List<AbroadAdmissionForm> getAllAdmissionForms() {
        return repository.findAll();
    }

    @Override
    public List<AbroadAdmissionForm> getAllByBranch(String branchCode) {
        return repository.findByBranchCode(branchCode);
    }
    @Override
    public AdmissionFormPersonalAcademicDTO getPersonalAcademicById(Long id) {
        return repository.findPersonalAcademicById(id);
    }
}
