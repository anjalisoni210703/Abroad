package com.abroad.Service;

import com.abroad.DTO.AbroadContinentDTO;

import java.util.List;

public interface AbroadHierarchyService {
    AbroadContinentDTO getHierarchyByContinentId(Long continentId);
    List<AbroadContinentDTO> getAllHierarchies();
}