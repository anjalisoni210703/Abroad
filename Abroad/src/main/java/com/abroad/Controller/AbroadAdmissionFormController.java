package com.abroad.Controller;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import com.abroad.Service.AbroadAdmissionFormService;
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
            @RequestPart("form") AbroadAdmissionForm form,
            @RequestPart(value = "sop", required = false) MultipartFile sop,
            @RequestPart(value = "lors", required = false) MultipartFile lors,
            @RequestPart(value = "resume", required = false) MultipartFile resume,
            @RequestPart(value = "testScores", required = false) MultipartFile testScores,
            @RequestPart(value = "passportCopy", required = false) MultipartFile passportCopy,
            @RequestPart(value = "studentVisa", required = false) MultipartFile studentVisa,
            @RequestPart(value = "passportPhotos", required = false) MultipartFile passportPhotos,
            @RequestParam String role,
            @RequestParam String email) {

        form.setRole(role);
        form.setCreatedByEmail(email);

        AbroadAdmissionForm createdForm = service.createAdmissionForm(
                form, role, email,
                sop, lors, resume, testScores,
                passportCopy, studentVisa, passportPhotos
        );

        return ResponseEntity.ok(createdForm);
    }


    // ✅ Update
    @PutMapping("/update/{id}")
    public ResponseEntity<AbroadAdmissionForm> update(
            @PathVariable Long id,
            @RequestPart("form") AbroadAdmissionForm form,
            @RequestPart(value = "sop", required = false) MultipartFile sopFile,
            @RequestPart(value = "lors", required = false) MultipartFile lorsFile,
            @RequestPart(value = "resume", required = false) MultipartFile resumeFile,
            @RequestPart(value = "testScores", required = false) MultipartFile testScoresFile,
            @RequestPart(value = "passportCopy", required = false) MultipartFile passportCopyFile,
            @RequestPart(value = "studentVisa", required = false) MultipartFile studentVisaFile,
            @RequestPart(value = "passportPhotos", required = false) MultipartFile passportPhotosFile,
            @RequestParam String role,
            @RequestParam String email) {

        form.setRole(role);
        form.setCreatedByEmail(email);

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

    @GetMapping("/PersonalInformation/{id}")
    public ResponseEntity<AdmissionFormPersonalAcademicDTO> getPersonalAcademicInfo(
            @PathVariable Long id) {

        AdmissionFormPersonalAcademicDTO dto = service.getPersonalAcademicById(id);
        return ResponseEntity.ok(dto);
    }
}