package com.abroad.Service;

import com.abroad.Entity.AbroadLead;
import com.abroad.Entity.LeadVisit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeadVisitService {

    LeadVisit addVisit(Long lead_id, String role, String email, String remark, String visitCount, String status);

    List<LeadVisit> getAllVisits(String role, String email);

    LeadVisit getVisitById(Long id, String role, String email);

    Void deleteVisit(Long id, String role, String email);

    List<LeadVisit> getVisitByLeadId(Long lead_id, String role, String email);
}
