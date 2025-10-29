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


    // ✅ Create Admission Form
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AbroadAdmissionForm> create(
            @RequestPart("form") String formJson,

            // Existing document fields
            @RequestPart(value = "sop", required = false) MultipartFile sop,
            @RequestPart(value = "sop2", required = false) MultipartFile sop2,
            @RequestPart(value = "lors", required = false) MultipartFile lors,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            @RequestPart(value = "testScores", required = false) MultipartFile testScores,
            @RequestPart(value = "passportCopy", required = false) MultipartFile passportCopy,
            @RequestPart(value = "passportInHandPhoto", required = false) MultipartFile passportInHandPhoto,
            @RequestPart(value = "studentVisa", required = false) MultipartFile studentVisa,
            @RequestPart(value = "passportPhotos", required = false) MultipartFile passportPhotos,
            @RequestPart(value = "moiCertificate", required = false) MultipartFile moiCertificate,
            @RequestPart(value = "moiWithSealAndSign", required = false) MultipartFile moiWithSealAndSign,
            @RequestPart(value = "workOrInternshipExperienceCertificate", required = false) MultipartFile workOrInternshipExperienceCertificate,
            @RequestPart(value = "tenthDigitalMarksheet", required = false) MultipartFile tenthDigitalMarksheet,
            @RequestPart(value = "twelfthDigitalMarksheet", required = false) MultipartFile twelfthDigitalMarksheet,
            @RequestPart(value = "degreeMarkList", required = false) MultipartFile degreeMarkList,
            @RequestPart(value = "transcripts", required = false) MultipartFile transcripts,
            @RequestPart(value = "bonafideCertificate", required = false) MultipartFile bonafideCertificate,
            @RequestPart(value = "fatherPanCard", required = false) MultipartFile fatherPanCard,
            @RequestPart(value = "fatherITR1", required = false) MultipartFile fatherITR1,
            @RequestPart(value = "fatherITR2", required = false) MultipartFile fatherITR2,
            @RequestPart(value = "fatherITR3", required = false) MultipartFile fatherITR3,
            @RequestPart(value = "fatherBankStatement", required = false) MultipartFile fatherBankStatement,
            @RequestPart(value = "bankBalanceCertificate", required = false) MultipartFile bankBalanceCertificate,
            @RequestPart(value = "parentsIDProof", required = false) MultipartFile parentsIDProof,
            @RequestPart(value = "bankStatement", required = false) MultipartFile bankStatement,

            // Common fields
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam(required = false) String branchCode
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadAdmissionForm form = mapper.readValue(formJson, AbroadAdmissionForm.class);

        // Set metadata fields
        form.setCreatedByEmail(email);
        form.setRole(role);

        AbroadAdmissionForm createdForm = service.createAdmissionForm(
                form,
                role,
                email,
                branchCode,
                sop,
                sop2,
                lors,
                resume,
                testScores,
                passportCopy,
                passportInHandPhoto,
                studentVisa,
                passportPhotos,
                moiCertificate,
                moiWithSealAndSign,
                workOrInternshipExperienceCertificate,
                tenthDigitalMarksheet,
                twelfthDigitalMarksheet,
                degreeMarkList,
                transcripts,
                bonafideCertificate,
                fatherPanCard,
                fatherITR1,
                fatherITR2,
                fatherITR3,
                fatherBankStatement,
                bankBalanceCertificate,
                parentsIDProof,
                bankStatement
        );

        return ResponseEntity.ok(createdForm);
    }






    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AbroadAdmissionForm> update(
            @PathVariable Long id,
            @RequestPart("form") String formJson,
            @RequestPart(value = "sop", required = false) MultipartFile sop,
            @RequestPart(value = "sop2", required = false) MultipartFile sop2,
            @RequestPart(value = "lors", required = false) MultipartFile lors,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            @RequestPart(value = "testScores", required = false) MultipartFile testScores,
            @RequestPart(value = "passportCopy", required = false) MultipartFile passportCopy,
            @RequestPart(value = "passportInHandPhoto", required = false) MultipartFile passportInHandPhoto,
            @RequestPart(value = "studentVisa", required = false) MultipartFile studentVisa,
            @RequestPart(value = "passportPhotos", required = false) MultipartFile passportPhotos,
            @RequestPart(value = "moiCertificate", required = false) MultipartFile moiCertificate,
            @RequestPart(value = "moiWithSealAndSign", required = false) MultipartFile moiWithSealAndSign,
            @RequestPart(value = "workOrInternshipExperienceCertificate", required = false) MultipartFile workOrInternshipExperienceCertificate,
            @RequestPart(value = "tenthDigitalMarksheet", required = false) MultipartFile tenthDigitalMarksheet,
            @RequestPart(value = "twelfthDigitalMarksheet", required = false) MultipartFile twelfthDigitalMarksheet,
            @RequestPart(value = "degreeMarkList", required = false) MultipartFile degreeMarkList,
            @RequestPart(value = "transcripts", required = false) MultipartFile transcripts,
            @RequestPart(value = "bonafideCertificate", required = false) MultipartFile bonafideCertificate,
            @RequestPart(value = "fatherPanCard", required = false) MultipartFile fatherPanCard,
            @RequestPart(value = "fatherITR1", required = false) MultipartFile fatherITR1,
            @RequestPart(value = "fatherITR2", required = false) MultipartFile fatherITR2,
            @RequestPart(value = "fatherITR3", required = false) MultipartFile fatherITR3,
            @RequestPart(value = "fatherBankStatement", required = false) MultipartFile fatherBankStatement,
            @RequestPart(value = "bankBalanceCertificate", required = false) MultipartFile bankBalanceCertificate,
            @RequestPart(value = "parentsIDProof", required = false) MultipartFile parentsIDProof,
            @RequestPart(value = "bankStatement", required = false) MultipartFile bankStatement,
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
                sop,
                sop2,
                lors,
                resume,
                testScores,
                passportCopy,
                passportInHandPhoto,
                studentVisa,
                passportPhotos,
                moiCertificate,
                moiWithSealAndSign,
                workOrInternshipExperienceCertificate,
                tenthDigitalMarksheet,
                twelfthDigitalMarksheet,
                degreeMarkList,
                transcripts,
                bonafideCertificate,
                fatherPanCard,
                fatherITR1,
                fatherITR2,
                fatherITR3,
                fatherBankStatement,
                bankBalanceCertificate,
                parentsIDProof,
                bankStatement
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