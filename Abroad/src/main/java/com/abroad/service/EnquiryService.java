package com.abroad.service;

import com.abroad.dto.EnquiryDTO;

import java.util.List;

public interface EnquiryService {
    EnquiryDTO saveEnquiry(EnquiryDTO enquiryDTO);
    List<EnquiryDTO> getAllEnquiries();

}
