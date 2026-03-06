package com.tapsyrys.tapsyrys.DTO.Requests;

import lombok.Data;

@Data
public class ShopSignupRequest {
    private String name;
    private String username;
    private String location;
    private String phone;
    private String password;
}
