package com.abroad.Service;

import com.abroad.Entity.AbroadBecomePartner;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AbroadBecomePartnerService {
    AbroadBecomePartner createPartner(AbroadBecomePartner partner);

    Page<AbroadBecomePartner> getAllPartners(String role, String email, int page, int size, AbroadBecomePartner partner);

    AbroadBecomePartner getPartnerById(Long id, String role, String email);

    AbroadBecomePartner updatePartner(Long id, AbroadBecomePartner partner, String role, String email);

    void deletePartner(Long id, String role, String email);

    Map<String, Map<String,String>> UploadPdf(Long id, String role, String email, MultipartFile contract, MultipartFile commission, MultipartFile gst, MultipartFile pan) throws IOException;

    Page<AbroadBecomePartner> filterPartners(String name, String email, String businessEmail, String instituteType,
                                             String contractType, String conductedBy, String status,
                                             String role, String createdByEmail, int page, int size);
}