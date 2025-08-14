package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadContactUs;
import com.abroad.Repository.AbroadContactUsRepository;
import com.abroad.Service.AbroadContactUsService;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbroadContactUsServiceImpl implements AbroadContactUsService {

    @Autowired
    private AbroadContactUsRepository repository;

    @Autowired
    private PermissionService permissionService;
    @Override
    public AbroadContactUs createContactUs(AbroadContactUs contactUs){
        return  repository.save(contactUs);
    }

    @Override
    public AbroadContactUs getContactUsById(Long id, String role, String email){
        if (!permissionService.hasPermission(role, email, "Get")) {
            throw new RuntimeException("Access Denied");
        }
        return repository.findById(id).get();
    }

    @Override
    public List<AbroadContactUs> getAllContactUs(String role, String email){
        if (!permissionService.hasPermission(role, email, "Get")) {
            throw new RuntimeException("Access Denied");
        }
        return repository.findAll();
    }

    @Override
    public AbroadContactUs update(Long id, AbroadContactUs uContactUs, String role, String email){
        if (!permissionService.hasPermission(role, email, "Get")) {
            throw new RuntimeException("Access Denied");
        }
        AbroadContactUs existing=repository.findById(id).get();
        if(uContactUs.getContinent()!=null) existing.setContinent(uContactUs.getContinent());
        if(uContactUs.getName()!=null) existing.setName(uContactUs.getName());
        if(uContactUs.getCourses()!=null) existing.setCourses(uContactUs.getCourses());
        if(uContactUs.getEmail()!=null) existing.setEmail(uContactUs.getEmail());
        if(uContactUs.getPhnNo()!=null) existing.setPhnNo(uContactUs.getPhnNo());

        return repository.save(existing);

    }

    @Override
    public Void delete(Long id, String role, String email){
        if (!permissionService.hasPermission(role, email, "Delete")) {
            throw new RuntimeException("Access Denied");
        }
        repository.deleteById(id);
        return null;
    }
}
