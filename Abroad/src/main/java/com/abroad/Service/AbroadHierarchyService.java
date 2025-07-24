package com.abroad.Service;

import com.abroad.DTO.AbroadContinentDTO;

public interface AbroadHierarchyService {
    AbroadContinentDTO getHierarchyByContinentId(Long continentId);
}