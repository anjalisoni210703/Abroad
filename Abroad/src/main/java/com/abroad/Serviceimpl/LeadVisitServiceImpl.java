package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadEnquiry;
import com.abroad.Entity.AbroadLeadVisit;
import com.abroad.Repository.EnquiryRepository;
import com.abroad.Repository.LeadVisitRepository;
import com.abroad.Service.LeadVisitService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeadVisitServiceImpl implements LeadVisitService {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private LeadVisitRepository leadVisitRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public AbroadLeadVisit addVisit(Long enquiry_id, String role, String email, String remark, String visitCount, String status){
        if (!permissionService.hasPermission(role, email, "Post")) {
            throw new AccessDeniedException("No permission to view lead");
        }
        AbroadEnquiry enquiry=enquiryRepository.findById(enquiry_id).get();

            AbroadLeadVisit visit=new AbroadLeadVisit();
            enquiry.setStatus(status);
            visit.setEnquiry(enquiry);
            visit.setStatus(status);
            visit.setVisitCount(visitCount);
            visit.setVisitDate(LocalDate.now());
            visit.setRemark(remark);
            visit.setCreatedByEmail(email);
            visit.setRole(role);


            return leadVisitRepository.save(visit);
    }
    @Override
    public List<AbroadLeadVisit> getAllVisits(String role, String email){
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission");
        }
        return leadVisitRepository.findAll();
    }

    @Override
    public AbroadLeadVisit getVisitById(Long id, String role, String email){
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission");
        }
        return leadVisitRepository.findById(id).get();
    }

    @Override
    public Void deleteVisit(Long id, String role, String email){
        if (!permissionService.hasPermission(role, email, "Delete")) {
            throw new AccessDeniedException("No permission");
        }
        leadVisitRepository.deleteById(id);
        return null;
    }

    @Override
    public List<AbroadLeadVisit> getVisitByLeadId(Long lead_id, String role, String email){
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view leads");
        }

        List<AbroadLeadVisit> visits=enquiryRepository.findById(lead_id).get().getLeadVisits();
        return visits;
    }

}
