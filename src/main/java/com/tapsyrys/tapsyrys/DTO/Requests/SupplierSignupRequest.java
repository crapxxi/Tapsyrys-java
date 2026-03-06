package com.tapsyrys.tapsyrys.DTO.Requests;

import lombok.Data;

@Data
public class SupplierSignupRequest {
    private String name;
    private String username;
    private String category;
    private String phone;
    private String password;
}
