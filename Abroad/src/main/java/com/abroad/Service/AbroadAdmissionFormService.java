package com.abroad.Service;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AbroadAdmissionFormService {
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
    );

    AbroadAdmissionForm updateAdmissionForm(
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
    );
    void deleteAdmissionForm(Long id,String role,String email);
    AbroadAdmissionForm getAdmissionFormById(Long id);
    List<AbroadAdmissionForm> getAllAdmissionForms();
    List<AbroadAdmissionForm> getAllByBranch(String branchCode);
    AdmissionFormPersonalAcademicDTO getPersonalAcademicById(Long id);

    List<AbroadAdmissionForm> getAllByCreatedByEmail(String createdByEmail);
    Map<String, Object> getOverview();
}