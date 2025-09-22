package com.abroad.Service;

import com.abroad.DTO.AdmissionFormPersonalAcademicDTO;
import com.abroad.Entity.AbroadAdmissionForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AbroadAdmissionFormService {
    public AbroadAdmissionForm createAdmissionForm(AbroadAdmissionForm form, String role, String email, MultipartFile sopFile,
                                                   MultipartFile lorsFile,
                                                   MultipartFile resumeFile,
                                                   MultipartFile testScoresFile,
                                                   MultipartFile passportCopyFile,
                                                   MultipartFile studentVisaFile,
                                                   MultipartFile passportPhotosFile);
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
            MultipartFile passportPhotosFile);
    void deleteAdmissionForm(Long id,String role,String email);
    AbroadAdmissionForm getAdmissionFormById(Long id);
    List<AbroadAdmissionForm> getAllAdmissionForms();
    List<AbroadAdmissionForm> getAllByBranch(String branchCode);
    AdmissionFormPersonalAcademicDTO getPersonalAcademicById(Long id);
}