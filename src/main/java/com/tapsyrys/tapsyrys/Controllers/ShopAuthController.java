package com.tapsyrys.tapsyrys.Controllers;

import com.tapsyrys.tapsyrys.DTO.Requests.LoginRequest;
import com.tapsyrys.tapsyrys.DTO.Requests.ShopSignupRequest;
import com.tapsyrys.tapsyrys.DTO.Responses.JwtResponse;
import com.tapsyrys.tapsyrys.Models.Shop;
import com.tapsyrys.tapsyrys.Repositories.ShopRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/shop")
@RequiredArgsConstructor
@Tag(name = "Авторизация магазина")
public class ShopAuthController {
    private final AuthenticationManager authenticationManager;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtCore jwtCore;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация")
    public ResponseEntity<?> shopRegister(@RequestBody ShopSignupRequest signupRequest) {
        if(shopRepository.existsByPhone(signupRequest.getPhone()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Этот номер занят!");

        Shop shop = new Shop();
        shop.setName(signupRequest.getName());
        shop.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        shop.setPhone(signupRequest.getPhone());
        shop.setLocation(signupRequest.getLocation());
        shop.setUsername(signupRequest.getUsername());
        shopRepository.save(shop);

        return ResponseEntity.ok("Магазин зарегестрирован");
    }

    @PostMapping("/login")
    @Operation(summary = "Аутентификация")
    public ResponseEntity<?> shopLogin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPhone(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt, "Bearer"));
    }
}
