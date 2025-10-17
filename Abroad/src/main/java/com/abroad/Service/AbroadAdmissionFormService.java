package com.abroad.Service;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AbroadAdmissionFormService {
    AbroadAdmissionForm createAdmissionForm(AbroadAdmissionForm form,
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
                                            MultipartFile bankStatementFile);
    AbroadAdmissionForm updateAdmissionForm(
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
            MultipartFile bankStatementFile);
    void deleteAdmissionForm(Long id,String role,String email);
    AbroadAdmissionForm getAdmissionFormById(Long id);
    List<AbroadAdmissionForm> getAllAdmissionForms();
    List<AbroadAdmissionForm> getAllByBranch(String branchCode);
    AdmissionFormPersonalAcademicDTO getPersonalAcademicById(Long id);

    List<AbroadAdmissionForm> getAllByCreatedByEmail(String createdByEmail);
}