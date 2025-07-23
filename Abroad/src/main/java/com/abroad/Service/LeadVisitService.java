package com.abroad.Service;

import com.abroad.Entity.AbroadLeadVisit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeadVisitService {

    AbroadLeadVisit addVisit(Long lead_id, String role, String email, String remark, String visitCount, String status);

    List<AbroadLeadVisit> getAllVisits(String role, String email);

    AbroadLeadVisit getVisitById(Long id, String role, String email);

    Void deleteVisit(Long id, String role, String email);

    List<AbroadLeadVisit> getVisitByLeadId(Long lead_id, String role, String email);
}
