package com.abroad.Service;

import com.abroad.Entity.AbroadState;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StateService {
    AbroadState createState(AbroadState state, MultipartFile image, Long countryId, String role, String email);
    List<AbroadState> getAllStates(String role, String email, Long countryId);
    AbroadState getStateById(Long id, String role, String email);
    AbroadState updateState(Long id, AbroadState state, MultipartFile image, Long countryId, String role, String email);
    void deleteState(Long id, String role, String email);
    List<String> searchStateNames(String name);
}