package com.abroad.Service;

import com.abroad.Entity.AbroadBecomePartner;

import java.util.List;

public interface AbroadBecomePartnerService {
    AbroadBecomePartner createPartner(AbroadBecomePartner partner, String role, String email);
    List<AbroadBecomePartner> getAllPartners(String role, String email, String branchCode);
    AbroadBecomePartner getPartnerById(Long id, String role, String email, String branchCode);
    AbroadBecomePartner updatePartner(Long id, AbroadBecomePartner partner, String role, String email);
    void deletePartner(Long id, String role, String email);
}