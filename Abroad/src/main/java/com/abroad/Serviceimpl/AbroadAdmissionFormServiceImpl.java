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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class AbroadAdmissionFormServiceImpl implements AbroadAdmissionFormService {

    @Autowired
    private AbroadAdmissionFormRepository repository;


    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadAdmissionForm createAdmissionForm(
            AbroadAdmissionForm form,
            String role,
            String email,
            String branchCode,
            MultipartFile sop,
            MultipartFile sop2,
            MultipartFile lors,
            MultipartFile resume,
            MultipartFile testScores,
            MultipartFile passportCopy,
            MultipartFile passportInHandPhoto,
            MultipartFile studentVisa,
            MultipartFile passportPhotos,
            MultipartFile moiCertificate,
            MultipartFile moiWithSealAndSign,
            MultipartFile workOrInternshipExperienceCertificate,
            MultipartFile tenthDigitalMarksheet,
            MultipartFile twelfthDigitalMarksheet,
            MultipartFile degreeMarkList,
            MultipartFile transcripts,
            MultipartFile bonafideCertificate,
            MultipartFile fatherPanCard,
            MultipartFile fatherITR1,
            MultipartFile fatherITR2,
            MultipartFile fatherITR3,
            MultipartFile fatherBankStatement,
            MultipartFile bankBalanceCertificate,
            MultipartFile parentsIDProof,
            MultipartFile bankStatement
    ) {
        // ✅ Permission check
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to Create AdmissionForm");
        }

        // ✅ Assign branch code
            String code = permissionService.fetchBranchCode(role, email);
            form.setBranchCode(code);


        try {
            // ✅ Upload core documents
            if (sop != null && !sop.isEmpty()) form.setSop(s3Service.uploadImage(sop));
            if (sop2 != null && !sop2.isEmpty()) form.setSop2(s3Service.uploadImage(sop2));
            if (lors != null && !lors.isEmpty()) form.setLors(s3Service.uploadImage(lors));
            if (resume != null && !resume.isEmpty()) form.setResume(s3Service.uploadImage(resume));
            if (testScores != null && !testScores.isEmpty()) form.setTestScores(s3Service.uploadImage(testScores));
            if (passportCopy != null && !passportCopy.isEmpty()) form.setPassportCopy(s3Service.uploadImage(passportCopy));
            if (passportInHandPhoto != null && !passportInHandPhoto.isEmpty()) form.setPassportInHandPhoto(s3Service.uploadImage(passportInHandPhoto));
            if (studentVisa != null && !studentVisa.isEmpty()) form.setStudentVisa(s3Service.uploadImage(studentVisa));
            if (passportPhotos != null && !passportPhotos.isEmpty()) form.setPassportPhotos(s3Service.uploadImage(passportPhotos));

            // ✅ Upload MOI / work / academic docs
            if (moiCertificate != null && !moiCertificate.isEmpty()) form.setMoiCertificate(s3Service.uploadImage(moiCertificate));
            if (moiWithSealAndSign != null && !moiWithSealAndSign.isEmpty()) form.setMoiWithSealAndSign(s3Service.uploadImage(moiWithSealAndSign));
            if (workOrInternshipExperienceCertificate != null && !workOrInternshipExperienceCertificate.isEmpty())
                form.setWorkOrInternshipExperienceCertificate(s3Service.uploadImage(workOrInternshipExperienceCertificate));
            if (tenthDigitalMarksheet != null && !tenthDigitalMarksheet.isEmpty())
                form.setTenthDigitalMarksheet(s3Service.uploadImage(tenthDigitalMarksheet));
            if (twelfthDigitalMarksheet != null && !twelfthDigitalMarksheet.isEmpty())
                form.setTwelfthDigitalMarksheet(s3Service.uploadImage(twelfthDigitalMarksheet));
            if (degreeMarkList != null && !degreeMarkList.isEmpty())
                form.setDegreeMarkList(s3Service.uploadImage(degreeMarkList));
            if (transcripts != null && !transcripts.isEmpty())
                form.setTranscripts(s3Service.uploadImage(transcripts));
            if (bonafideCertificate != null && !bonafideCertificate.isEmpty())
                form.setBonafideCertificate(s3Service.uploadImage(bonafideCertificate));

            // ✅ Upload financial & parent documents
            if (fatherPanCard != null && !fatherPanCard.isEmpty()) form.setFatherPanCard(s3Service.uploadImage(fatherPanCard));
            if (fatherITR1 != null && !fatherITR1.isEmpty()) form.setFatherITR1(s3Service.uploadImage(fatherITR1));
            if (fatherITR2 != null && !fatherITR2.isEmpty()) form.setFatherITR2(s3Service.uploadImage(fatherITR2));
            if (fatherITR3 != null && !fatherITR3.isEmpty()) form.setFatherITR3(s3Service.uploadImage(fatherITR3));
            if (fatherBankStatement != null && !fatherBankStatement.isEmpty()) form.setFatherBankStatement(s3Service.uploadImage(fatherBankStatement));
            if (bankBalanceCertificate != null && !bankBalanceCertificate.isEmpty()) form.setBankBalanceCertificate(s3Service.uploadImage(bankBalanceCertificate));
            if (parentsIDProof != null && !parentsIDProof.isEmpty()) form.setParentsIDProof(s3Service.uploadImage(parentsIDProof));
            if (bankStatement != null && !bankStatement.isEmpty()) form.setBankStatement(s3Service.uploadImage(bankStatement));

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to upload one or more files", e);
        }

        // ✅ Set timestamps
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
            MultipartFile sop,
            MultipartFile sop2,
            MultipartFile lors,
            MultipartFile resume,
            MultipartFile testScores,
            MultipartFile passportCopy,
            MultipartFile passportInHandPhoto,
            MultipartFile studentVisa,
            MultipartFile passportPhotos,
            MultipartFile moiCertificate,
            MultipartFile moiWithSealAndSign,
            MultipartFile workOrInternshipExperienceCertificate,
            MultipartFile tenthDigitalMarksheet,
            MultipartFile twelfthDigitalMarksheet,
            MultipartFile degreeMarkList,
            MultipartFile transcripts,
            MultipartFile bonafideCertificate,
            MultipartFile fatherPanCard,
            MultipartFile fatherITR1,
            MultipartFile fatherITR2,
            MultipartFile fatherITR3,
            MultipartFile fatherBankStatement,
            MultipartFile bankBalanceCertificate,
            MultipartFile parentsIDProof,
            MultipartFile bankStatement
    ) {

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
        existing.setRole(role);
        existing.setCreatedByEmail(email);

        // Branch code logic
        if (!"SUPERADMIN".equalsIgnoreCase(role)) {
            existing.setBranchCode(permissionService.fetchBranchCode(role, email));
        } else if (form.getBranchCode() != null) {
            existing.setBranchCode(form.getBranchCode());
        }

        // ---------- Conditional S3 upload & replace ----------
        try {
            // Utility method for clarity (optional)
            if (sop != null && !sop.isEmpty()) replaceFile(existing::getSop, existing::setSop, sop);
            if (sop2 != null && !sop2.isEmpty()) replaceFile(existing::getSop2, existing::setSop2, sop2);
            if (lors != null && !lors.isEmpty()) replaceFile(existing::getLors, existing::setLors, lors);
            if (resume != null && !resume.isEmpty()) replaceFile(existing::getResume, existing::setResume, resume);
            if (testScores != null && !testScores.isEmpty()) replaceFile(existing::getTestScores, existing::setTestScores, testScores);
            if (passportCopy != null && !passportCopy.isEmpty()) replaceFile(existing::getPassportCopy, existing::setPassportCopy, passportCopy);
            if (passportInHandPhoto != null && !passportInHandPhoto.isEmpty()) replaceFile(existing::getPassportInHandPhoto, existing::setPassportInHandPhoto, passportInHandPhoto);
            if (studentVisa != null && !studentVisa.isEmpty()) replaceFile(existing::getStudentVisa, existing::setStudentVisa, studentVisa);
            if (passportPhotos != null && !passportPhotos.isEmpty()) replaceFile(existing::getPassportPhotos, existing::setPassportPhotos, passportPhotos);
            if (moiCertificate != null && !moiCertificate.isEmpty()) replaceFile(existing::getMoiCertificate, existing::setMoiCertificate, moiCertificate);
            if (moiWithSealAndSign != null && !moiWithSealAndSign.isEmpty()) replaceFile(existing::getMoiWithSealAndSign, existing::setMoiWithSealAndSign, moiWithSealAndSign);
            if (workOrInternshipExperienceCertificate != null && !workOrInternshipExperienceCertificate.isEmpty()) replaceFile(existing::getWorkOrInternshipExperienceCertificate, existing::setWorkOrInternshipExperienceCertificate, workOrInternshipExperienceCertificate);
            if (tenthDigitalMarksheet != null && !tenthDigitalMarksheet.isEmpty()) replaceFile(existing::getTenthDigitalMarksheet, existing::setTenthDigitalMarksheet, tenthDigitalMarksheet);
            if (twelfthDigitalMarksheet != null && !twelfthDigitalMarksheet.isEmpty()) replaceFile(existing::getTwelfthDigitalMarksheet, existing::setTwelfthDigitalMarksheet, twelfthDigitalMarksheet);
            if (degreeMarkList != null && !degreeMarkList.isEmpty()) replaceFile(existing::getDegreeMarkList, existing::setDegreeMarkList, degreeMarkList);
            if (transcripts != null && !transcripts.isEmpty()) replaceFile(existing::getTranscripts, existing::setTranscripts, transcripts);
            if (bonafideCertificate != null && !bonafideCertificate.isEmpty()) replaceFile(existing::getBonafideCertificate, existing::setBonafideCertificate, bonafideCertificate);
            if (fatherPanCard != null && !fatherPanCard.isEmpty()) replaceFile(existing::getFatherPanCard, existing::setFatherPanCard, fatherPanCard);
            if (fatherITR1 != null && !fatherITR1.isEmpty()) replaceFile(existing::getFatherITR1, existing::setFatherITR1, fatherITR1);
            if (fatherITR2 != null && !fatherITR2.isEmpty()) replaceFile(existing::getFatherITR2, existing::setFatherITR2, fatherITR2);
            if (fatherITR3 != null && !fatherITR3.isEmpty()) replaceFile(existing::getFatherITR3, existing::setFatherITR3, fatherITR3);
            if (fatherBankStatement != null && !fatherBankStatement.isEmpty()) replaceFile(existing::getFatherBankStatement, existing::setFatherBankStatement, fatherBankStatement);
            if (bankBalanceCertificate != null && !bankBalanceCertificate.isEmpty()) replaceFile(existing::getBankBalanceCertificate, existing::setBankBalanceCertificate, bankBalanceCertificate);
            if (parentsIDProof != null && !parentsIDProof.isEmpty()) replaceFile(existing::getParentsIDProof, existing::setParentsIDProof, parentsIDProof);
            if (bankStatement != null && !bankStatement.isEmpty()) replaceFile(existing::getBankStatement, existing::setBankStatement, bankStatement);

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

    // Utility method for concise file replacement
    private void replaceFile(Supplier<String> getter, Consumer<String> setter, MultipartFile file) throws IOException {
        String oldUrl = getter.get();
        if (oldUrl != null) s3Service.deleteImage(oldUrl);
        setter.accept(s3Service.uploadImage(file));
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

    @Override
    public Map<String, Object> getOverview() {
        Map<String, Object> response = new LinkedHashMap<>();

        // ✅ Total count
        long total = repository.count();

        // ✅ Status-wise counts
        List<Object[]> rows = repository.findStatusCounts();
        Map<String, Long> statusCounts = new LinkedHashMap<>();

        // ensure all statuses appear, even if zero
        List<String> expectedStatuses = Arrays.asList(
                "Applied",
                "In Review",
                "Accepted",
                "Rejected",
                "Visa Processing",
                "Visa Approved",
                "Visa Rejected"
        );
        for (String s : expectedStatuses) statusCounts.put(s, 0L);

        for (Object[] row : rows) {
            String status = row[0] == null ? "Unknown" : row[0].toString();
            Long count = (Long) row[1];
            statusCounts.put(status, statusCounts.getOrDefault(status, 0L) + count);
        }

        // ✅ Date-wise counts
        Map<String, Long> dateCounts = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        dateCounts.put("Todays", repository.countByCreatedDateTimeBetween(todayStart, now));

        LocalDateTime last7Start = LocalDate.now().minusDays(6).atStartOfDay();
        dateCounts.put("last7days", repository.countByCreatedDateTimeBetween(last7Start, now));

        LocalDateTime last30Start = LocalDate.now().minusDays(29).atStartOfDay();
        dateCounts.put("last30days", repository.countByCreatedDateTimeBetween(last30Start, now));

        LocalDateTime last365Start = LocalDate.now().minusDays(364).atStartOfDay();
        dateCounts.put("last365days", repository.countByCreatedDateTimeBetween(last365Start, now));

        // ✅ Final JSON response
        response.put("total", total);
        response.put("statusCounts", statusCounts);
        response.put("dateCounts", dateCounts);
        return response;
    }


}
