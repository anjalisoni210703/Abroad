package com.abroad.service;

import com.abroad.dto.EnquiryDTO;
import com.abroad.dto.EnquiryFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnquiryService {
    EnquiryDTO saveEnquiry(EnquiryDTO enquiryDTO);
    Page<EnquiryDTO> getAllEnquiries(EnquiryFilterDTO filterDTO, Pageable pageable);

}
