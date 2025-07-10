package com.abroad.Serviceimpl;

import com.abroad.Entity.AbroadUser;
import com.abroad.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final WebClient webClient;

    @Autowired
    private StaffService staffService;

    @Autowired
    private AbroadUserServiceImpl abroadUserService;

    @Autowired
    public PermissionServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
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
            case "STAFF" -> {
                Map<String, Boolean> perms = staffService.getPermissionsByEmail(email);
                yield switch (action.toUpperCase()) {
                    case "GET" -> Boolean.TRUE.equals(perms.get("cansGet"));
                    case "POST" -> Boolean.TRUE.equals(perms.get("cansPost"));
                    case "PUT" -> Boolean.TRUE.equals(perms.get("cansPut"));
                    case "DELETE" -> Boolean.TRUE.equals(perms.get("cansDelete"));
                    default -> false;
                };
            }
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
            case "USER" -> {
                Map<String, Object> perms = abroadUserService.getPermissionsByEmail(email);
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
                    case "GET" -> Boolean.TRUE.equals(perms.get("cansGet"));
                    case "POST" -> Boolean.TRUE.equals(perms.get("cansPost"));
                    case "PUT" -> Boolean.TRUE.equals(perms.get("cansPut"));
                    case "DELETE" -> Boolean.TRUE.equals(perms.get("cansDelete"));
                    default -> false;
                };
            }
            default -> false;
        };

    }

    @Override
    public String fetchBranchCode(String role, String email) {
        String branchCode = switch (role.toUpperCase()) {
            case "BRANCH" -> webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/branch/getbranchcode")
                            .queryParam("email", email)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            case "STAFF" -> webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/staff/getbranchcode")
                            .queryParam("email", email)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            case "USER" -> abroadUserService.getUserByEmail(email)
                    .map(AbroadUser::getBranchCode)
                    .orElse(null);
            case "SUPERADMIN" -> null;
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };

        // Only enforce non-null for non-admin
        if (!"SUPERADMIN".equalsIgnoreCase(role) && (branchCode == null || branchCode.trim().isEmpty())) {
            throw new IllegalArgumentException("Branch code is required");
        }

        return branchCode;
    }


}
