package com.abroad.Serviceimpl;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import com.abroad.Repository.AbroadAdmissionFormRepository;
import com.abroad.Service.AbroadAdmissionFormService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public AbroadAdmissionForm createAdmissionForm(AbroadAdmissionForm form,
                                                   String role,
                                                   String email,
                                                   String branchCode,
                                                   MultipartFile sopFile,
                                                   MultipartFile lorsFile,
                                                   MultipartFile resumeFile,
                                                   MultipartFile testScoresFile,
                                                   MultipartFile passportCopyFile,
                                                   MultipartFile studentVisaFile,
                                                   MultipartFile passportPhotosFile,
                                                   MultipartFile moiCertificateFile,
                                                   MultipartFile workExpFile,
                                                   MultipartFile sscMarksheetFile,
                                                   MultipartFile hscMarksheetFile,
                                                   MultipartFile bachelorsMarksheetFile,
                                                   MultipartFile transcriptsFile,
                                                   MultipartFile bonafideCertificateFile,
                                                   MultipartFile parentsIDProofFile,
                                                   MultipartFile bankStatementFile) {
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to Create AdmissionForm");
        }

        if (!"SUPERADMIN".equalsIgnoreCase(role)) {
            String code = permissionService.fetchBranchCode(role, email);
            form.setBranchCode(code);
        } else if (branchCode != null && !branchCode.isEmpty()) {
            form.setBranchCode(branchCode);
        }

        try {
            // core documents
            if (sopFile != null && !sopFile.isEmpty()) {
                form.setSop(s3Service.uploadImage(sopFile));
            }
            if (lorsFile != null && !lorsFile.isEmpty()) {
                form.setLors(s3Service.uploadImage(lorsFile));
            }
            if (resumeFile != null && !resumeFile.isEmpty()) {
                form.setResume(s3Service.uploadImage(resumeFile));
            }
            if (testScoresFile != null && !testScoresFile.isEmpty()) {
                form.setTestScores(s3Service.uploadImage(testScoresFile));
            }
            if (passportCopyFile != null && !passportCopyFile.isEmpty()) {
                form.setPassportCopy(s3Service.uploadImage(passportCopyFile));
            }
            if (studentVisaFile != null && !studentVisaFile.isEmpty()) {
                form.setStudentVisa(s3Service.uploadImage(studentVisaFile));
            }
            if (passportPhotosFile != null && !passportPhotosFile.isEmpty()) {
                form.setPassportPhotos(s3Service.uploadImage(passportPhotosFile));
            }

            // newly added documents
            if (moiCertificateFile != null && !moiCertificateFile.isEmpty()) {
                form.setMOICertificate(s3Service.uploadImage(moiCertificateFile));
            }
            if (workExpFile != null && !workExpFile.isEmpty()) {
                form.setWorkOrInternshipExperienceCertificate(s3Service.uploadImage(workExpFile));
            }
            if (sscMarksheetFile != null && !sscMarksheetFile.isEmpty()) {
                form.setSSCMarksheet(s3Service.uploadImage(sscMarksheetFile));
            }
            if (hscMarksheetFile != null && !hscMarksheetFile.isEmpty()) {
                form.setHSCMarksheet(s3Service.uploadImage(hscMarksheetFile));
            }
            if (bachelorsMarksheetFile != null && !bachelorsMarksheetFile.isEmpty()) {
                form.setBachelorsMarksheet(s3Service.uploadImage(bachelorsMarksheetFile));
            }
            if (transcriptsFile != null && !transcriptsFile.isEmpty()) {
                form.setTranscripts(s3Service.uploadImage(transcriptsFile)); // note field name in entity
            }
            if (bonafideCertificateFile != null && !bonafideCertificateFile.isEmpty()) {
                form.setBonafideCertificate(s3Service.uploadImage(bonafideCertificateFile));
            }
            if (parentsIDProofFile != null && !parentsIDProofFile.isEmpty()) {
                form.setParentsIDProof(s3Service.uploadImage(parentsIDProofFile));
            }
            if (bankStatementFile != null && !bankStatementFile.isEmpty()) {
                form.setBankStatement(s3Service.uploadImage(bankStatementFile));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file(s)", e);
        }

        form.setCreatedDateTime(LocalDateTime.now());

        try {
            return repository.save(form);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Email already exists, please use a different one.");
        }
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
            MultipartFile passportPhotosFile,
            MultipartFile moiCertificateFile,
            MultipartFile workExpFile,
            MultipartFile sscMarksheetFile,
            MultipartFile hscMarksheetFile,
            MultipartFile bachelorsMarksheetFile,
            MultipartFile transcriptsFile,
            MultipartFile bonafideCertificateFile,
            MultipartFile parentsIDProofFile,
            MultipartFile bankStatementFile) {

        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Admission Form");
        }

        AbroadAdmissionForm existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission form not found with id " + id));

        // ---------- Update simple fields ----------
        existing.setFullName(form.getFullName() != null ? form.getFullName() : existing.getFullName());
        existing.setEmail(form.getEmail() != null ? form.getEmail() : existing.getEmail());
        existing.setPhone(form.getPhone() != null ? form.getPhone() : existing.getPhone());
        existing.setAlternatePhone(form.getAlternatePhone() != null ? form.getAlternatePhone() : existing.getAlternatePhone());
        existing.setCountry(form.getCountry() != null ? form.getCountry() : existing.getCountry());
        existing.setUniversity(form.getUniversity() != null ? form.getUniversity() : existing.getUniversity());
        existing.setCourse(form.getCourse() != null ? form.getCourse() : existing.getCourse());
        existing.setStream(form.getStream() != null ? form.getStream() : existing.getStream());
        existing.setPassoutYear(form.getPassoutYear() != null ? form.getPassoutYear() : existing.getPassoutYear());
        existing.setStatus(form.getStatus() != null ? form.getStatus() : existing.getStatus());
        existing.setIntake(form.getIntake() != null ? form.getIntake() : existing.getIntake());
        existing.setNotes(form.getNotes() != null ? form.getNotes() : existing.getNotes());

        // Role + createdByEmail update (as you had)
        existing.setRole(role);
        existing.setCreatedByEmail(email);

        // Branch code behavior: only allow SUPERADMIN to set branchCode; otherwise keep permissionService value
        if (!"SUPERADMIN".equalsIgnoreCase(role)) {
            String code = permissionService.fetchBranchCode(role, email);
            existing.setBranchCode(code);
        } else {
            if (form.getBranchCode() != null) {
                existing.setBranchCode(form.getBranchCode());
            }
        }

        // ---------- Upload new documents and delete old ones if replaced ----------
        try {
            if (sopFile != null && !sopFile.isEmpty()) {
                if (existing.getSop() != null) s3Service.deleteImage(existing.getSop());
                existing.setSop(s3Service.uploadImage(sopFile));
            }
            if (lorsFile != null && !lorsFile.isEmpty()) {
                if (existing.getLors() != null) s3Service.deleteImage(existing.getLors());
                existing.setLors(s3Service.uploadImage(lorsFile));
            }
            if (resumeFile != null && !resumeFile.isEmpty()) {
                if (existing.getResume() != null) s3Service.deleteImage(existing.getResume());
                existing.setResume(s3Service.uploadImage(resumeFile));
            }
            if (testScoresFile != null && !testScoresFile.isEmpty()) {
                if (existing.getTestScores() != null) s3Service.deleteImage(existing.getTestScores());
                existing.setTestScores(s3Service.uploadImage(testScoresFile));
            }
            if (passportCopyFile != null && !passportCopyFile.isEmpty()) {
                if (existing.getPassportCopy() != null) s3Service.deleteImage(existing.getPassportCopy());
                existing.setPassportCopy(s3Service.uploadImage(passportCopyFile));
            }
            if (studentVisaFile != null && !studentVisaFile.isEmpty()) {
                if (existing.getStudentVisa() != null) s3Service.deleteImage(existing.getStudentVisa());
                existing.setStudentVisa(s3Service.uploadImage(studentVisaFile));
            }
            if (passportPhotosFile != null && !passportPhotosFile.isEmpty()) {
                if (existing.getPassportPhotos() != null) s3Service.deleteImage(existing.getPassportPhotos());
                existing.setPassportPhotos(s3Service.uploadImage(passportPhotosFile));
            }

            // newly added docs
            if (moiCertificateFile != null && !moiCertificateFile.isEmpty()) {
                if (existing.getMOICertificate() != null) s3Service.deleteImage(existing.getMOICertificate());
                existing.setMOICertificate(s3Service.uploadImage(moiCertificateFile));
            }
            if (workExpFile != null && !workExpFile.isEmpty()) {
                if (existing.getWorkOrInternshipExperienceCertificate() != null)
                    s3Service.deleteImage(existing.getWorkOrInternshipExperienceCertificate());
                existing.setWorkOrInternshipExperienceCertificate(s3Service.uploadImage(workExpFile));
            }
            if (sscMarksheetFile != null && !sscMarksheetFile.isEmpty()) {
                if (existing.getSSCMarksheet() != null) s3Service.deleteImage(existing.getSSCMarksheet());
                existing.setSSCMarksheet(s3Service.uploadImage(sscMarksheetFile));
            }
            if (hscMarksheetFile != null && !hscMarksheetFile.isEmpty()) {
                if (existing.getHSCMarksheet() != null) s3Service.deleteImage(existing.getHSCMarksheet());
                existing.setHSCMarksheet(s3Service.uploadImage(hscMarksheetFile));
            }
            if (bachelorsMarksheetFile != null && !bachelorsMarksheetFile.isEmpty()) {
                if (existing.getBachelorsMarksheet() != null) s3Service.deleteImage(existing.getBachelorsMarksheet());
                existing.setBachelorsMarksheet(s3Service.uploadImage(bachelorsMarksheetFile));
            }
            if (transcriptsFile != null && !transcriptsFile.isEmpty()) {
                if (existing.getTranscripts() != null) s3Service.deleteImage(existing.getTranscripts());
                existing.setTranscripts(s3Service.uploadImage(transcriptsFile));
            }
            if (bonafideCertificateFile != null && !bonafideCertificateFile.isEmpty()) {
                if (existing.getBonafideCertificate() != null) s3Service.deleteImage(existing.getBonafideCertificate());
                existing.setBonafideCertificate(s3Service.uploadImage(bonafideCertificateFile));
            }
            if (parentsIDProofFile != null && !parentsIDProofFile.isEmpty()) {
                if (existing.getParentsIDProof() != null) s3Service.deleteImage(existing.getParentsIDProof());
                existing.setParentsIDProof(s3Service.uploadImage(parentsIDProofFile));
            }
            if (bankStatementFile != null && !bankStatementFile.isEmpty()) {
                if (existing.getBankStatement() != null) s3Service.deleteImage(existing.getBankStatement());
                existing.setBankStatement(s3Service.uploadImage(bankStatementFile));
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload document(s) to S3", e);
        }

        // ---------- Save ----------
        try {
            return repository.save(existing);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Email already exists, please use a different one.");
        }
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

    @Override
    public List<AbroadAdmissionForm> getAllByCreatedByEmail(String createdByEmail) {
        return repository.findByCreatedByEmail(createdByEmail);
    }
}
