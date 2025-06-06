package com.abroad.serviceimpl;

import com.abroad.dto.AddressDTO;
import com.abroad.dto.EnquiryDTO;
import com.abroad.dto.EnquiryFilterDTO;
import com.abroad.entity.Address;
import com.abroad.entity.Enquiry;
import com.abroad.repository.EnquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnquiryServiceImpl implements com.abroad.service.EnquiryService {
    @Autowired
    private EnquiryRepository enquiryRepository;

    @Override
    public EnquiryDTO saveEnquiry(EnquiryDTO enquiryDTO) {
        // Convert DTO to Entity
        Enquiry enquiry = toEntity(enquiryDTO);

        // Save Enquiry (with Address due to CascadeType.ALL)
        Enquiry savedEnquiry = enquiryRepository.save(enquiry);

        // Convert back to DTO if needed
        return toDTO(savedEnquiry);
    }

    // Mapping: DTO -> Entity
    private Enquiry toEntity(EnquiryDTO dto) {
        Enquiry enquiry = new Enquiry();
        enquiry.setName(dto.getName());
        enquiry.setPhone_no(dto.getPhoneNo()); // updated
        enquiry.setEmail(dto.getEmail());
        enquiry.setBatch(dto.getBatch());
        enquiry.setSourceby(dto.getSourceBy()); // updated
        enquiry.setConducts(dto.getConducts());
        enquiry.setStatus(dto.getStatus());
        enquiry.setEnquiry_date(dto.getEnquiryDate()); // updated
        enquiry.setRemark(dto.getRemark());
        enquiry.setDob(dto.getDob());
        enquiry.setGender(dto.getGender());
        enquiry.setMothertounge(dto.getMotherTounge()); // updated
        enquiry.setFatherprofession(dto.getFatherProfession()); // updated
        enquiry.setEducationqualification(dto.getEducationQualification()); // updated
        enquiry.setAnnualincome(dto.getAnnualIncome()); // updated
        enquiry.setPhotoUrl(dto.getPhotoUrl());

        if (dto.getAddress() != null) {
            AddressDTO addrDTO = dto.getAddress();
            Address address = new Address();
            address.setAddress(addrDTO.getAddress());
            address.setLandmark(addrDTO.getLandmark());
            address.setState(addrDTO.getState());
            address.setDistrict(addrDTO.getDistrict());
            enquiry.setAddress(address);
        } else {
            enquiry.setAddress(null);
        }

        return enquiry;
    }

    // Entity to DTO
    private EnquiryDTO toDTO(Enquiry enquiry) {
        EnquiryDTO dto = new EnquiryDTO();
        dto.setName(enquiry.getName());
        dto.setPhoneNo(enquiry.getPhone_no()); // updated
        dto.setEmail(enquiry.getEmail());
        dto.setBatch(enquiry.getBatch());
        dto.setSourceBy(enquiry.getSourceby()); // updated
        dto.setConducts(enquiry.getConducts());
        dto.setStatus(enquiry.getStatus());
        dto.setEnquiryDate(enquiry.getEnquiry_date()); // updated
        dto.setRemark(enquiry.getRemark());
        dto.setDob(enquiry.getDob());
        dto.setGender(enquiry.getGender());
        dto.setMotherTounge(enquiry.getMothertounge()); // updated
        dto.setFatherProfession(enquiry.getFatherprofession()); // updated
        dto.setEducationQualification(enquiry.getEducationqualification()); // updated
        dto.setAnnualIncome(enquiry.getAnnualincome()); // updated
        dto.setPhotoUrl(enquiry.getPhotoUrl());

        if (enquiry.getAddress() != null) {
            Address address = enquiry.getAddress();
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setAddress(address.getAddress());
            addressDTO.setLandmark(address.getLandmark());
            addressDTO.setState(address.getState());
            addressDTO.setDistrict(address.getDistrict());
            dto.setAddress(addressDTO);
        } else {
            dto.setAddress(null);
        }

        return dto;
    }
    @Override
    public Page<EnquiryDTO> getAllEnquiries(EnquiryFilterDTO filterDTO, Pageable pageable) {
        Specification<Enquiry> spec = Specification.where(null);

        if (filterDTO != null) {
            if (filterDTO.getName() != null && !filterDTO.getName().isBlank()) {
                spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + filterDTO.getName().toLowerCase() + "%"));
            }
            if (filterDTO.getStatus() != null && !filterDTO.getStatus().isBlank()) {
                spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("status")), filterDTO.getStatus().toLowerCase()));
            }
            if (filterDTO.getBatch() != null && !filterDTO.getBatch().isBlank()) {
                spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("batch")), filterDTO.getBatch().toLowerCase()));
            }
            if (filterDTO.getEmail() != null && !filterDTO.getEmail().isBlank()) {
                spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("email")), filterDTO.getEmail().toLowerCase()));
            }
            if (filterDTO.getGender() != null && !filterDTO.getGender().isBlank()) {
                spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("gender")), filterDTO.getGender().toLowerCase()));
            }
        }

        Page<Enquiry> enquiryPage = enquiryRepository.findAll(spec, pageable);
        return enquiryPage.map(this::toDTO);
    }

}
