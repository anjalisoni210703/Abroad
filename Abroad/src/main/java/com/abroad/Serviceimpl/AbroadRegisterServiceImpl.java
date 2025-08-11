package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadRegister;
import com.abroad.Repository.AbroadRegisterRepository;
import com.abroad.Service.AbroadRegisterService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AbroadRegisterServiceImpl implements AbroadRegisterService {

    private final WebClient webClient;

    @Autowired
    private StaffService staffService;

    @Autowired
    private AbroadRegisterRepository registerRepository;

    public AbroadRegisterServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    public boolean hasPermission(String role, String email, String action) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            Boolean exists = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/superAdmin/existByEmail")
                            .queryParam("email", email)
                            .build())
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            return Boolean.TRUE.equals(exists);
        }

        return switch (role.toUpperCase()) {
            case "BRANCH" -> {
                Map<String, Object> perms = staffService.getCrudPermissionForBranchtByEmail(email);
                yield switch (action.toUpperCase()) {
                    case "GET" -> Boolean.TRUE.equals(perms.get("candGet"));
                    case "POST" -> Boolean.TRUE.equals(perms.get("candPost"));
                    case "PUT" -> Boolean.TRUE.equals(perms.get("candPut"));
                    case "DELETE" -> Boolean.TRUE.equals(perms.get("candDelete"));
                    default -> false;
                };
            }
            case "SUPERADMIN" -> {
                Map<String, Object> perms = staffService.getCrudPermissionForAdminByEmail(email);
                yield switch (action.toUpperCase()) {
                    case "GET" -> Boolean.TRUE.equals(perms.get("cansGet")); // FIXED HERE
                    default -> false;
                };
            }
            default -> false;
        };
    }

    @Override
    public AbroadRegister createRegister(String role, String email, AbroadRegister register){
        if(!hasPermission(role,email,"Post")){
            throw new RuntimeException("Access Denied");
        }
        register.setDate(LocalDate.now());
        return registerRepository.save(register);
    }

    @Override
    public AbroadRegister getById(Long id, String role, String email){
        if(!hasPermission(role,email,"Get")){
            throw new RuntimeException("Access Denied");
        }
        return registerRepository.findById(id).get();
    }

    @Override
    public List<AbroadRegister> getAll(String role, String email){
        if(!hasPermission(role,email,"GET")){
            throw new RuntimeException("Access denied");
        }
        return registerRepository.findAll();
    }

    @Override
    public AbroadRegister update(Long id, String role, String email, AbroadRegister uregister){
        if(!hasPermission(role,email,"Put")){
            throw new RuntimeException("Access denied");
        }
        AbroadRegister existing=registerRepository.findById(id).get();
        existing.setAmount(uregister.getAmount()!=null? uregister.getAmount() : existing.getAmount());
        existing.setEmail(uregister.getEmail()!=null? uregister.getEmail() : existing.getEmail());
        existing.setCourse(uregister.getCourse()!=null? uregister.getCourse() : existing.getCourse());
        existing.setLocation(uregister.getLocation()!=null? uregister.getLocation() : existing.getLocation());
        existing.setPhoneNo(uregister.getPhoneNo()!=null? uregister.getPhoneNo() : existing.getPhoneNo());
        existing.setStream(uregister.getStream()!=null? uregister.getStream() : existing.getStream());
        existing.setDate(LocalDate.now());

        return registerRepository.save(existing);
    }

    @Override
    public Void delete(Long id, String role, String email){
        if(!hasPermission(role,email,"Delete")){
            throw new RuntimeException("Access denied");
        }
        registerRepository.deleteById(id);
        return null;
    }
}

