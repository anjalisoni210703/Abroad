package com.abroad.Controller;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import com.abroad.Service.AbroadAdmissionFormService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admissionForms")
public class AbroadAdmissionFormController {

    @Autowired
    private AbroadAdmissionFormService service;

    // ✅ Create
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AbroadAdmissionForm> create(
            @RequestPart("form") String formJson,
            @RequestPart(value = "sop", required = false) MultipartFile sop,
            @RequestPart(value = "lors", required = false) MultipartFile lors,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            @RequestPart(value = "testScores", required = false) MultipartFile testScores,
            @RequestPart(value = "passportCopy", required = false) MultipartFile passportCopy,
            @RequestPart(value = "studentVisa", required = false) MultipartFile studentVisa,
            @RequestPart(value = "passportPhotos", required = false) MultipartFile passportPhotos,
            @RequestPart(value = "moiCertificate", required = false) MultipartFile moiCertificate,
            @RequestPart(value = "workExp", required = false) MultipartFile workExp,
            @RequestPart(value = "sscMarksheet", required = false) MultipartFile sscMarksheet,
            @RequestPart(value = "hscMarksheet", required = false) MultipartFile hscMarksheet,
            @RequestPart(value = "bachelorsMarksheet", required = false) MultipartFile bachelorsMarksheet,
            @RequestPart(value = "transcripts", required = false) MultipartFile transcripts,
            @RequestPart(value = "bonafideCertificate", required = false) MultipartFile bonafideCertificate,
            @RequestPart(value = "parentsIDProof", required = false) MultipartFile parentsIDProof,
            @RequestPart(value = "bankStatement", required = false) MultipartFile bankStatement,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam(required = false) String branchCode) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadAdmissionForm form = mapper.readValue(formJson, AbroadAdmissionForm.class);

        // ensure these fields are set (createdBy, role) — service also checks permissions and branch handling
        form.setCreatedByEmail(email);
        form.setRole(role);

        AbroadAdmissionForm createdForm = service.createAdmissionForm(
                form,
                role,
                email,
                branchCode,
                sop,
                lors,
                resume,
                testScores,
                passportCopy,
                studentVisa,
                passportPhotos,
                moiCertificate,
                workExp,
                sscMarksheet,
                hscMarksheet,
                bachelorsMarksheet,
                transcripts,
                bonafideCertificate,
                parentsIDProof,
                bankStatement
        );

        return ResponseEntity.ok(createdForm);
    }





    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AbroadAdmissionForm> update(
            @PathVariable Long id,
            @RequestPart("form") String formJson,
            @RequestPart(value = "sop", required = false) MultipartFile sopFile,
            @RequestPart(value = "lors", required = false) MultipartFile lorsFile,
            @RequestPart(value = "resume", required = false) MultipartFile resumeFile,
            @RequestPart(value = "testScores", required = false) MultipartFile testScoresFile,
            @RequestPart(value = "passportCopy", required = false) MultipartFile passportCopyFile,
            @RequestPart(value = "studentVisa", required = false) MultipartFile studentVisaFile,
            @RequestPart(value = "passportPhotos", required = false) MultipartFile passportPhotosFile,
            @RequestPart(value = "moiCertificate", required = false) MultipartFile moiCertificateFile,
            @RequestPart(value = "workExp", required = false) MultipartFile workExpFile,
            @RequestPart(value = "sscMarksheet", required = false) MultipartFile sscMarksheetFile,
            @RequestPart(value = "hscMarksheet", required = false) MultipartFile hscMarksheetFile,
            @RequestPart(value = "bachelorsMarksheet", required = false) MultipartFile bachelorsMarksheetFile,
            @RequestPart(value = "transcripts", required = false) MultipartFile transcriptsFile,
            @RequestPart(value = "bonafideCertificate", required = false) MultipartFile bonafideCertificateFile,
            @RequestPart(value = "parentsIDProof", required = false) MultipartFile parentsIDProofFile,
            @RequestPart(value = "bankStatement", required = false) MultipartFile bankStatementFile,
            @RequestParam String role,
            @RequestParam String email
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadAdmissionForm form = mapper.readValue(formJson, AbroadAdmissionForm.class);

        form.setRole(role);
        form.setCreatedByEmail(email);

        AbroadAdmissionForm updatedForm = service.updateAdmissionForm(
                id,
                form,
                role,
                email,
                sopFile,
                lorsFile,
                resumeFile,
                testScoresFile,
                passportCopyFile,
                studentVisaFile,
                passportPhotosFile,
                moiCertificateFile,
                workExpFile,
                sscMarksheetFile,
                hscMarksheetFile,
                bachelorsMarksheetFile,
                transcriptsFile,
                bonafideCertificateFile,
                parentsIDProofFile,
                bankStatementFile
        );

        return ResponseEntity.ok(updatedForm);
    }



    // ✅ Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id,
            @RequestParam String role,
            @RequestParam String email) {

        // (Optional) you can log who deleted it
        service.deleteAdmissionForm(id,role,email);
        return ResponseEntity.ok("Admission form deleted by: " + email + " (role: " + role + ")");
    }

    // Get by ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<AbroadAdmissionForm> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAdmissionFormById(id));
    }

    // Get all
    @GetMapping("/getAll")
    public ResponseEntity<List<AbroadAdmissionForm>> getAll() {
        return ResponseEntity.ok(service.getAllAdmissionForms());
    }

    // Get all by branch
    @GetMapping("/getAllByBranch/{branchCode}")
    public ResponseEntity<List<AbroadAdmissionForm>> getAllByBranch(@PathVariable String branchCode) {
        return ResponseEntity.ok(service.getAllByBranch(branchCode));
    }

    @GetMapping("/getAllForStaff/{createdByEmail}")
    public ResponseEntity<List<AbroadAdmissionForm>> getAllByCreatedByEmail(@PathVariable String createdByEmail) {
        return ResponseEntity.ok(service.getAllByCreatedByEmail(createdByEmail));
    }

    @GetMapping("/PersonalInformation/{id}")
    public ResponseEntity<AdmissionFormPersonalAcademicDTO> getPersonalAcademicInfo(
            @PathVariable Long id) {

        AdmissionFormPersonalAcademicDTO dto = service.getPersonalAcademicById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/statusCount")
    public ResponseEntity<Map<String, Object>> getOverview() {
        Map<String, Object> response = service.getOverview();
        return ResponseEntity.ok(response);
    }

}