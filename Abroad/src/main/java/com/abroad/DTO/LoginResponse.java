package com.abroad.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private Map<String, Object> data;
    private String massage;

}
