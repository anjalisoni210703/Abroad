package com.abroad.Controller;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import com.abroad.Service.AbroadAdmissionFormService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admissionForms")
public class AbroadAdmissionFormController {

    @Autowired
    private AbroadAdmissionFormService service;

    // ✅ Create
    @PostMapping("/create")
    public ResponseEntity<AbroadAdmissionForm> create(
            @RequestPart("form") String formJson,
            @RequestParam(value = "sop", required = false) MultipartFile sop,
            @RequestParam(value = "lors", required = false) MultipartFile lors,
            @RequestParam(value = "resume", required = false) MultipartFile resume,
            @RequestParam(value = "testScores", required = false) MultipartFile testScores,
            @RequestParam(value = "passportCopy", required = false) MultipartFile passportCopy,
            @RequestParam(value = "studentVisa", required = false) MultipartFile studentVisa,
            @RequestParam(value = "passportPhotos", required = false) MultipartFile passportPhotos,
            @RequestParam String role,
            @RequestParam String email,
            @RequestParam(required = false) String branchCode
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Deserialize JSON string into AbroadAdmissionForm object
        AbroadAdmissionForm form = mapper.readValue(formJson, AbroadAdmissionForm.class);

        // Set extra fields
        form.setRole(role);
        form.setCreatedByEmail(email);

        AbroadAdmissionForm createdForm = service.createAdmissionForm(
                form, role, email,branchCode,
                sop, lors, resume, testScores,
                passportCopy, studentVisa, passportPhotos
        );

        return ResponseEntity.ok(createdForm);
    }




    // ✅ Update
    @PutMapping("/update/{id}")
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
            @RequestParam String role,
            @RequestParam String email
    ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Convert JSON string into AbroadAdmissionForm object
        AbroadAdmissionForm form = mapper.readValue(formJson, AbroadAdmissionForm.class);

        // Set extra fields
        form.setRole(role);
        form.setCreatedByEmail(email);

        // Call service
        AbroadAdmissionForm updatedForm = service.updateAdmissionForm(
                id, form, role, email,
                sopFile, lorsFile, resumeFile, testScoresFile,
                passportCopyFile, studentVisaFile, passportPhotosFile
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
}