package com.abroad.Service;

import com.abroad.Entity.AbroadRegisterForm;
import java.util.List;

public interface AbroadRegisterFormService {
    AbroadRegisterForm createRegisterForm(AbroadRegisterForm form);
    List<AbroadRegisterForm> getAllRegisterForms(String role, String email);
    AbroadRegisterForm getRegisterFormById(int id, String role, String email);
    AbroadRegisterForm updateRegisterForm(int id, AbroadRegisterForm form, String role, String email);
    void deleteRegisterForm(int id, String role, String email);
}
