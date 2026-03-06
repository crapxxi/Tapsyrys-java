package com.tapsyrys.tapsyrys.Controllers;

import com.tapsyrys.tapsyrys.DTO.Requests.LoginRequest;
import com.tapsyrys.tapsyrys.DTO.Requests.SupplierSignupRequest;
import com.tapsyrys.tapsyrys.DTO.Responses.JwtResponse;
import com.tapsyrys.tapsyrys.DTO.Responses.SupplierResponse;
import com.tapsyrys.tapsyrys.Models.Supplier;
import com.tapsyrys.tapsyrys.Repositories.ShopRepository;
import com.tapsyrys.tapsyrys.Repositories.SupplierRepository;
import com.tapsyrys.tapsyrys.Security.JWT.JwtCore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@RestController
@RequestMapping("/api/v1/auth/supplier")
@RequiredArgsConstructor
@Tag(name="Авторизация поставщика")
public class SupplierAuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final SupplierRepository supplierRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация")
    public ResponseEntity<?> supplierRegister(@RequestBody SupplierSignupRequest supplierRequest) {
        if(supplierRepository.existsByPhone(supplierRequest.getPhone()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Этот номер занят!");

        Supplier supplier = new Supplier();
        supplier.setName(supplierRequest.getName());
        supplier.setUsername(supplierRequest.getUsername());
        supplier.setCategory(supplierRequest.getCategory());
        supplier.setPhone(supplierRequest.getPhone());
        supplier.setPassword(passwordEncoder.encode(supplierRequest.getPassword()));
        supplierRepository.save(supplier);
        return ResponseEntity.ok("Поставщик зарегестрирован!");
    }
    @Operation(summary = "Аутентификация")
    @PostMapping("/login")
    public ResponseEntity<?> supplierLogin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt, "Bearer"));
    }

}
