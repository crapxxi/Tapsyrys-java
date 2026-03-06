package com.tapsyrys.tapsyrys.Security.JWT;

import com.tapsyrys.tapsyrys.Interfaces.Authorizable;
import com.tapsyrys.tapsyrys.Models.Shop;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtCore {
    private String secret = "mySuperSecretKeyForTapsyrysApplication2026FullSecure";

    private int lifeTime = 8640000;

    Key key = Keys.hmacShaKeyFor(secret.getBytes());
    public String generateToken(Authentication authentication) {
        String phone = "";
        if(authentication.getPrincipal() instanceof Authorizable user)
            phone = user.getPhone();
        else if(authentication.getPrincipal() instanceof UserDetails userDetails)
            phone = userDetails.getUsername();
        else
            phone = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(phone)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()  +lifeTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String getPhoneFromJwt(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
