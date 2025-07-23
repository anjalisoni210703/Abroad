package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadBecomePartner;
import com.abroad.Exception.ResourceNotFoundException;
import com.abroad.Pagination.PatnerSpecification;
import com.abroad.Repository.AbroadBecomePartnerRepository;
import com.abroad.Service.AbroadBecomePartnerService;
import com.abroad.Service.PermissionService;
import com.abroad.Service.S3Service;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AbroadBecomePartnerServiceImpl implements AbroadBecomePartnerService {

    @Autowired
    private AbroadBecomePartnerRepository repository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private S3Service s3Service;

    @Override
    public AbroadBecomePartner createPartner(AbroadBecomePartner partner) {
//        if (!permissionService.hasPermission(role, email, "POST")) {
//            throw new AccessDeniedException("No permission to create partner");
//        }

//        String branchCode = permissionService.fetchBranchCode(role, email);
//        partner.setCreatedByEmail(email);
//        partner.setRole(role);
//        partner.setBranchCode(branchCode);

        return repository.save(partner);
    }

    @Override
    public Page<AbroadBecomePartner> getAllPartners(String role, String email, int page, int size, AbroadBecomePartner partner) {
        if (!permissionService.hasPermission(role, email, "Post")) {
            throw new AccessDeniedException("No permission to view partners");
        }
//        return repository.findAllByBranchCode(branchCode);
        Pageable pageable = PageRequest.of(page,size);
        Specification<AbroadBecomePartner> spec= PatnerSpecification.build(partner);
        return repository.findAll(spec,pageable);
    }

    @Override
    public AbroadBecomePartner getPartnerById(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "GET")) {
            throw new AccessDeniedException("No permission to view partner");
        }

        AbroadBecomePartner partner = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

//        if (!partner.getBranchCode().equals(branchCode)) {
//            throw new AccessDeniedException("Access denied to this branch data");
//        }

        return partner;
    }

    @Override
    public AbroadBecomePartner updatePartner(Long id, AbroadBecomePartner updated, String role, String email) {
        if (!permissionService.hasPermission(role, email, "PUT")) {
            throw new AccessDeniedException("No permission to update partner");
        }

        AbroadBecomePartner existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

        existing.setPartnerName(updated.getPartnerName() != null ? updated.getPartnerName() : existing.getPartnerName());
        existing.setBusinessName(updated.getBusinessName() != null ? updated.getBusinessName() : existing.getBusinessName());
        existing.setPartnerEmail(updated.getPartnerEmail() != null ? updated.getPartnerEmail() : existing.getPartnerEmail());
        existing.setPartnerContact(updated.getPartnerContact() != null ? updated.getPartnerContact() : existing.getPartnerContact());
        existing.setPartnerCity(updated.getPartnerCity() != null ? updated.getPartnerCity() : existing.getPartnerCity());
        existing.setPartnerAddress(updated.getPartnerAddress()!=null?updated.getPartnerAddress():existing.getPartnerAddress());
        existing.setPartnerCountry(updated.getPartnerCountry()!=null?updated.getPartnerCountry():existing.getPartnerCountry());
        existing.setPartnerDistrict(updated.getPartnerDistrict()!=null?updated.getPartnerDistrict():existing.getPartnerDistrict());
        existing.setPartnerPincode(updated.getPartnerPincode()!=null?updated.getPartnerPincode():existing.getPartnerPincode());
        existing.setState(updated.getState()!=null?updated.getState():existing.getState());
        existing.setStatus(updated.getStatus()!=null?updated.getStatus():existing.getStatus());
        existing.setBusinessEmail(updated.getBusinessEmail()!=null?updated.getBusinessEmail():existing.getBusinessEmail());
        existing.setBusinessName(updated.getBusinessName()!=null?updated.getBusinessName():existing.getBusinessName());
        existing.setBusinessContact(updated.getBusinessContact()!=null?updated.getBusinessContact():existing.getBusinessContact());
        existing.setRemark(updated.getRemark()!=null?updated.getRemark():existing.getRemark());
        existing.setCommissionPercent(updated.getCommissionPercent()!=null?updated.getCommissionPercent():existing.getCommissionPercent());
        existing.setAuthorityDesignation(updated.getAuthorityDesignation()!=null?updated.getAuthorityDesignation():existing.getAuthorityDesignation());
        existing.setConductedBy(updated.getConductedBy()!=null?updated.getConductedBy():existing.getConductedBy());
        existing.setDesignation(updated.getDesignation()!=null?updated.getDesignation():existing.getDesignation());
        existing.setContractType(updated.getContractType()!=null?updated.getContractType():existing.getContractType());
        existing.setInstituteType(updated.getInstituteType()!=null?updated.getInstituteType():existing.getInstituteType());
        existing.setUniversity(updated.getUniversity()!=null?updated.getUniversity():existing.getUniversity());
        existing.setMobileNo(updated.getMobileNo()!=null?updated.getMobileNo():existing.getMobileNo());
        existing.setAuthorityName(updated.getAuthorityName()!=null?updated.getAuthorityName():existing.getAuthorityName());
        existing.setAuthorityName(updated.getAuthorityName()!=null?updated.getAuthorityName():existing.getAuthorityName());
        existing.setAuthorityContact(updated.getAuthorityContact()!=null?updated.getAuthorityContact():existing.getAuthorityContact());
        return repository.save(existing);
    }

    @Override
    public void deletePartner(Long id, String role, String email) {
        if (!permissionService.hasPermission(role, email, "DELETE")) {
            throw new AccessDeniedException("No permission to delete partner");
        }

        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

        repository.deleteById(id);
    }

    @Override
    public Map<String,Map<String,String>> UploadPdf(Long id, String role, String email, MultipartFile contract, MultipartFile commission, MultipartFile gst, MultipartFile pan) throws IOException {
        if (!permissionService.hasPermission(role, email, "Post")) {
            throw new AccessDeniedException("Access Denied");
        }
        AbroadBecomePartner partner=repository.findById(id).get();
        partner.setContractPdf(s3Service.uploadImage(contract));
        partner.setCommissionPdf(s3Service.uploadImage(commission));
        partner.setGstPdf(s3Service.uploadImage(gst));
        partner.setPanPdf(s3Service.uploadImage(pan));
        repository.save(partner);
        Map<String,String> pdfUrls=new HashMap<>();
        pdfUrls.put("Pan",partner.getPanPdf());
        pdfUrls.put("GSt",partner.getGstPdf());
        pdfUrls.put("Commission",partner.getCommissionPdf());
        pdfUrls.put("Contract",partner.getContractPdf());
        Map<String,Map<String,String>> urls=new HashMap<>();
        urls.put("PDFs",pdfUrls);
        return urls;
    }

    @Override
    public Page<AbroadBecomePartner> filterPartners(String name, String email, String businessEmail, String instituteType,
                                                    String contractType, String conductedBy, String status,
                                                    String role, String createdByEmail, int page, int size) {

        if (!permissionService.hasPermission(role, createdByEmail, "POST"))
            throw new AccessDeniedException("No permission");

        Specification<AbroadBecomePartner> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.trim().isEmpty())
                predicates.add(cb.like(cb.lower(root.get("partnerName")), "%" + name.toLowerCase() + "%"));

            if (email != null && !email.trim().isEmpty())
                predicates.add(cb.equal(cb.lower(root.get("partnerEmail")), email.toLowerCase()));

            if (businessEmail != null && !businessEmail.trim().isEmpty())
                predicates.add(cb.equal(cb.lower(root.get("businessEmail")), businessEmail.toLowerCase()));

            if (instituteType != null && !instituteType.trim().isEmpty())
                predicates.add(cb.equal(root.get("instituteType"), instituteType));

            if (contractType != null && !contractType.trim().isEmpty())
                predicates.add(cb.equal(root.get("contractType"), contractType));

            if (conductedBy != null && !conductedBy.trim().isEmpty())
                predicates.add(cb.equal(cb.lower(root.get("conductedBy")), conductedBy.toLowerCase()));

            if (status != null && !status.trim().isEmpty())
                predicates.add(cb.equal(cb.lower(root.get("status")), status.toLowerCase()));

            query.orderBy(cb.desc(root.get("id")));
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(spec, pageable);
    }
}