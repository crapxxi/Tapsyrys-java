package com.tapsyrys.tapsyrys.DTO.Requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}
