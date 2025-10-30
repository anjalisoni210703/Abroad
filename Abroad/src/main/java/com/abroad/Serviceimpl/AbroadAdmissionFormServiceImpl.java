package com.abroad.Serviceimpl;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import com.abroad.Repository.AbroadAdmissionFormRepository;
import com.abroad.Service.AbroadAdmissionFormService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
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
    @Transactional
    public AbroadAdmissionForm createAdmissionForm(
            AbroadAdmissionForm form,
            String role,
            String email,
            String branchCode,
            MultipartFile sop,
            MultipartFile sop2,
            MultipartFile lors,
            MultipartFile lorsTranscript2,
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
            MultipartFile diplomaMarkList,
            MultipartFile transcripts,
            MultipartFile bonafideCertificate,
            MultipartFile fatherPanCard,
            MultipartFile fatherITR1,
            MultipartFile fatherITR2,
            MultipartFile fatherITR3,
            MultipartFile fatherBankStatement,
            MultipartFile bankBalanceCertificate,
            MultipartFile fatherIDProof,
            MultipartFile motherIDProof,
            MultipartFile bankStatement
    ) {
        // Permission check
        if (!permissionService.hasPermission(role, email, "POST")) {
            throw new AccessDeniedException("No permission to Create AdmissionForm");
        }

        // Branch code: allow SUPERADMIN to pass branchCode param, others resolved from permissionService
        String code = permissionService.fetchBranchCode(role, email);
        form.setBranchCode(code);


        // Set role & creator
        form.setRole(role);
        form.setCreatedByEmail(email);

        // Set any additional simple fields that might be present in the incoming DTO (if you want override)
        // Optional.ofNullable(form.getGender()).ifPresent(form::setGender); // example, usually form already has these
        // If any date strings need parsing to LocalDate, do that here before uploading or saving
        form.setCreatedDateTime(LocalDateTime.now());

        // Prepare parallel uploads
        ExecutorService executor = Executors.newFixedThreadPool(8); // tune threads as needed
        List<Callable<Void>> tasks = new ArrayList<>();

        // Add tasks using helper (upload & set on form)
        addCreateUploadTask(tasks, form::getSop, form::setSop, sop); // getter not used here but kept for signature parity
        addCreateUploadTask(tasks, form::getSop2, form::setSop2, sop2);
        addCreateUploadTask(tasks, form::getLors, form::setLors, lors);
        addCreateUploadTask(tasks, form::getLorsTranscript2, form::setLorsTranscript2, lorsTranscript2);
        addCreateUploadTask(tasks, form::getResume, form::setResume, resume);
        addCreateUploadTask(tasks, form::getTestScores, form::setTestScores, testScores);
        addCreateUploadTask(tasks, form::getPassportCopy, form::setPassportCopy, passportCopy);
        addCreateUploadTask(tasks, form::getPassportInHandPhoto, form::setPassportInHandPhoto, passportInHandPhoto);
        addCreateUploadTask(tasks, form::getStudentVisa, form::setStudentVisa, studentVisa);
        addCreateUploadTask(tasks, form::getPassportPhotos, form::setPassportPhotos, passportPhotos);
        addCreateUploadTask(tasks, form::getMoiCertificate, form::setMoiCertificate, moiCertificate);
        addCreateUploadTask(tasks, form::getMoiWithSealAndSign, form::setMoiWithSealAndSign, moiWithSealAndSign);
        addCreateUploadTask(tasks, form::getWorkOrInternshipExperienceCertificate, form::setWorkOrInternshipExperienceCertificate, workOrInternshipExperienceCertificate);
        addCreateUploadTask(tasks, form::getTenthDigitalMarksheet, form::setTenthDigitalMarksheet, tenthDigitalMarksheet);
        addCreateUploadTask(tasks, form::getTwelfthDigitalMarksheet, form::setTwelfthDigitalMarksheet, twelfthDigitalMarksheet);
        addCreateUploadTask(tasks, form::getDegreeMarkList, form::setDegreeMarkList, degreeMarkList);
        addCreateUploadTask(tasks, form::getDiplomaMarkList, form::setDiplomaMarkList, diplomaMarkList);
        addCreateUploadTask(tasks, form::getTranscripts, form::setTranscripts, transcripts);
        addCreateUploadTask(tasks, form::getBonafideCertificate, form::setBonafideCertificate, bonafideCertificate);
        addCreateUploadTask(tasks, form::getFatherPanCard, form::setFatherPanCard, fatherPanCard);
        addCreateUploadTask(tasks, form::getFatherITR1, form::setFatherITR1, fatherITR1);
        addCreateUploadTask(tasks, form::getFatherITR2, form::setFatherITR2, fatherITR2);
        addCreateUploadTask(tasks, form::getFatherITR3, form::setFatherITR3, fatherITR3);
        addCreateUploadTask(tasks, form::getFatherBankStatement, form::setFatherBankStatement, fatherBankStatement);
        addCreateUploadTask(tasks, form::getBankBalanceCertificate, form::setBankBalanceCertificate, bankBalanceCertificate);
        addCreateUploadTask(tasks, form::getFatherIDProof, form::setFatherIDProof, fatherIDProof);
        addCreateUploadTask(tasks, form::getMotherIDProof, form::setMotherIDProof, motherIDProof);
        addCreateUploadTask(tasks, form::getBankStatement, form::setBankStatement, bankStatement);

        // Execute uploads in parallel
        try {
            if (!tasks.isEmpty()) {
                List<Future<Void>> futures = executor.invokeAll(tasks);
                // check for exceptions in tasks
                for (Future<Void> f : futures) {
                    try {
                        f.get(); // will rethrow execution exception if any
                    } catch (ExecutionException ee) {
                        throw new RuntimeException("Failed to upload one or more files", ee.getCause());
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("File upload interrupted", e);
        } finally {
            executor.shutdown();
        }

        // Final save
        try {
            return repository.save(form);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Email already exists, please use a different one.", e);
        }
    }

    // Helper used for create: creates a Callable that uploads file and sets the field on the form
    private void addCreateUploadTask(List<Callable<Void>> tasks,
                                     Supplier<String> getter,
                                     Consumer<String> setter,
                                     MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            tasks.add(() -> {
                // upload and set URL
                String url = s3Service.uploadImage(file);
                setter.accept(url);
                return null;
            });
        }
    }





    @Override
    @Transactional
    public AbroadAdmissionForm updateAdmissionForm(
            Long id,
            AbroadAdmissionForm form,
            String role,
            String email,
            MultipartFile sop,
            MultipartFile sop2,
            MultipartFile lors,
            MultipartFile lorsTranscript2,
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
            MultipartFile diplomaMarkList,
            MultipartFile transcripts,
            MultipartFile bonafideCertificate,
            MultipartFile fatherPanCard,
            MultipartFile fatherITR1,
            MultipartFile fatherITR2,
            MultipartFile fatherITR3,
            MultipartFile fatherBankStatement,
            MultipartFile bankBalanceCertificate,
            MultipartFile fatherIDProof,
            MultipartFile motherIDProof,
            MultipartFile bankStatement
    ) {

        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update Admission Form");
        }

        AbroadAdmissionForm existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission form not found with id " + id));

        // ---------- Update simple fields (strings) ----------
        // Basic personal fields
        existing.setFullName(form.getFullName() != null ? form.getFullName() : existing.getFullName());
        existing.setEmail(form.getEmail() != null ? form.getEmail() : existing.getEmail());
        existing.setPhone(form.getPhone() != null ? form.getPhone() : existing.getPhone());
        existing.setAlternatePhone(form.getAlternatePhone() != null ? form.getAlternatePhone() : existing.getAlternatePhone());

        // Added personal fields
        existing.setGender(form.getGender() != null ? form.getGender() : existing.getGender());
        existing.setPassportAvailable(form.getPassportAvailable() != null ? form.getPassportAvailable() : existing.getPassportAvailable());
        existing.setApplyFor(form.getApplyFor() != null ? form.getApplyFor() : existing.getApplyFor());
        existing.setContinent(form.getContinent() != null ? form.getContinent() : existing.getContinent());
        existing.setState(form.getState() != null ? form.getState() : existing.getState());
        existing.setCity(form.getCity() != null ? form.getCity() : existing.getCity());
        existing.setCollege(form.getCollege() != null ? form.getCollege() : existing.getCollege());
        existing.setHouseNumber(form.getHouseNumber() != null ? form.getHouseNumber() : existing.getHouseNumber());
        existing.setStreetName(form.getStreetName() != null ? form.getStreetName() : existing.getStreetName());
        existing.setLandmark(form.getLandmark() != null ? form.getLandmark() : existing.getLandmark());
        existing.setPincode(form.getPincode() != null ? form.getPincode() : existing.getPincode());

        // Academic / education fields
        existing.setCountry(form.getCountry() != null ? form.getCountry() : existing.getCountry());
        existing.setUniversity(form.getUniversity() != null ? form.getUniversity() : existing.getUniversity());
        existing.setCourse(form.getCourse() != null ? form.getCourse() : existing.getCourse());
        existing.setStream(form.getStream() != null ? form.getStream() : existing.getStream());
        existing.setPassedCourse(form.getPassedCourse() != null ? form.getPassedCourse() : existing.getPassedCourse());
        existing.setPercentage(form.getPercentage() != null ? form.getPercentage() : existing.getPercentage());
        existing.setGapInEducation(form.getGapInEducation() != null ? form.getGapInEducation() : existing.getGapInEducation());
        existing.setPassoutYear(form.getPassoutYear() != null ? form.getPassoutYear() : existing.getPassoutYear());
        existing.setStatus(form.getStatus() != null ? form.getStatus() : existing.getStatus());
        existing.setIntake(form.getIntake() != null ? form.getIntake() : existing.getIntake());
        existing.setNotes(form.getNotes() != null ? form.getNotes() : existing.getNotes());

        // Family info
        existing.setFatherOccupation(form.getFatherOccupation() != null ? form.getFatherOccupation() : existing.getFatherOccupation());
        existing.setFatherIncome(form.getFatherIncome() != null ? form.getFatherIncome() : existing.getFatherIncome());
        existing.setFatherPhone(form.getFatherPhone() != null ? form.getFatherPhone() : existing.getFatherPhone());

        // Staff info
        existing.setAssignedStaffName(form.getAssignedStaffName() != null ? form.getAssignedStaffName() : existing.getAssignedStaffName());
        existing.setAssignedStaffEmail(form.getAssignedStaffEmail() != null ? form.getAssignedStaffEmail() : existing.getAssignedStaffEmail());

        // Role/audit fields
        existing.setRole(role);
        existing.setCreatedByEmail(email);

        // Branch code logic (keep existing behavior)
        if (!"SUPERADMIN".equalsIgnoreCase(role)) {
            existing.setBranchCode(permissionService.fetchBranchCode(role, email));
        } else if (form.getBranchCode() != null) {
            existing.setBranchCode(form.getBranchCode());
        }

        // ---------- Update date fields (if model uses LocalDate/Date) ----------
        // If your form.getDob() returns String, convert appropriately before using
        if (form.getDob() != null) {
            existing.setDob(form.getDob()); // assume LocalDate or compatible type
        }
        if (form.getEnquiryDate() != null) {
            existing.setEnquiryDate(form.getEnquiryDate()); // assume LocalDate or compatible type
        }

        // ---------- Conditional S3 upload & replace ----------
        try {
            if (sop != null && !sop.isEmpty()) replaceFile(existing::getSop, existing::setSop, sop);
            if (sop2 != null && !sop2.isEmpty()) replaceFile(existing::getSop2, existing::setSop2, sop2);
            if (lors != null && !lors.isEmpty()) replaceFile(existing::getLors, existing::setLors, lors);
            if (lorsTranscript2 != null && !lorsTranscript2.isEmpty()) replaceFile(existing::getLorsTranscript2, existing::setLorsTranscript2, lorsTranscript2);
            if (resume != null && !resume.isEmpty()) replaceFile(existing::getResume, existing::setResume, resume);
            if (testScores != null && !testScores.isEmpty()) replaceFile(existing::getTestScores, existing::setTestScores, testScores);
            if (passportCopy != null && !passportCopy.isEmpty()) replaceFile(existing::getPassportCopy, existing::setPassportCopy, passportCopy);
            if (passportInHandPhoto != null && !passportInHandPhoto.isEmpty()) replaceFile(existing::getPassportInHandPhoto, existing::setPassportInHandPhoto, passportInHandPhoto);
            if (studentVisa != null && !studentVisa.isEmpty()) replaceFile(existing::getStudentVisa, existing::setStudentVisa, studentVisa);
            if (passportPhotos != null && !passportPhotos.isEmpty()) replaceFile(existing::getPassportPhotos, existing::setPassportPhotos, passportPhotos);
            if (moiCertificate != null && !moiCertificate.isEmpty()) replaceFile(existing::getMoiCertificate, existing::setMoiCertificate, moiCertificate);
            if (moiWithSealAndSign != null && !moiWithSealAndSign.isEmpty()) replaceFile(existing::getMoiWithSealAndSign, existing::setMoiWithSealAndSign, moiWithSealAndSign);
            if (workOrInternshipExperienceCertificate != null && !workOrInternshipExperienceCertificate.isEmpty())
                replaceFile(existing::getWorkOrInternshipExperienceCertificate, existing::setWorkOrInternshipExperienceCertificate, workOrInternshipExperienceCertificate);
            if (tenthDigitalMarksheet != null && !tenthDigitalMarksheet.isEmpty()) replaceFile(existing::getTenthDigitalMarksheet, existing::setTenthDigitalMarksheet, tenthDigitalMarksheet);
            if (twelfthDigitalMarksheet != null && !twelfthDigitalMarksheet.isEmpty()) replaceFile(existing::getTwelfthDigitalMarksheet, existing::setTwelfthDigitalMarksheet, twelfthDigitalMarksheet);
            if (degreeMarkList != null && !degreeMarkList.isEmpty()) replaceFile(existing::getDegreeMarkList, existing::setDegreeMarkList, degreeMarkList);
            if (diplomaMarkList != null && !diplomaMarkList.isEmpty()) replaceFile(existing::getDiplomaMarkList, existing::setDiplomaMarkList, diplomaMarkList);
            if (transcripts != null && !transcripts.isEmpty()) replaceFile(existing::getTranscripts, existing::setTranscripts, transcripts);
            if (bonafideCertificate != null && !bonafideCertificate.isEmpty()) replaceFile(existing::getBonafideCertificate, existing::setBonafideCertificate, bonafideCertificate);
            if (fatherPanCard != null && !fatherPanCard.isEmpty()) replaceFile(existing::getFatherPanCard, existing::setFatherPanCard, fatherPanCard);
            if (fatherITR1 != null && !fatherITR1.isEmpty()) replaceFile(existing::getFatherITR1, existing::setFatherITR1, fatherITR1);
            if (fatherITR2 != null && !fatherITR2.isEmpty()) replaceFile(existing::getFatherITR2, existing::setFatherITR2, fatherITR2);
            if (fatherITR3 != null && !fatherITR3.isEmpty()) replaceFile(existing::getFatherITR3, existing::setFatherITR3, fatherITR3);
            if (fatherBankStatement != null && !fatherBankStatement.isEmpty()) replaceFile(existing::getFatherBankStatement, existing::setFatherBankStatement, fatherBankStatement);
            if (bankBalanceCertificate != null && !bankBalanceCertificate.isEmpty()) replaceFile(existing::getBankBalanceCertificate, existing::setBankBalanceCertificate, bankBalanceCertificate);
            if (fatherIDProof != null && !fatherIDProof.isEmpty()) replaceFile(existing::getFatherIDProof, existing::setFatherIDProof, fatherIDProof);
            if (motherIDProof != null && !motherIDProof.isEmpty()) replaceFile(existing::getMotherIDProof, existing::setMotherIDProof, motherIDProof);
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
