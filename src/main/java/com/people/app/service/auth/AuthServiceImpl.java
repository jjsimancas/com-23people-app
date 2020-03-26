package com.people.app.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.people.app.config.Constants.ROLE_USER;
import static com.people.app.config.Constants.SECRET;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${value.jwtExpiration}")
    private int jwtExpiration;

    public String tokenGenerator(){
        List grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(ROLE_USER);
        List<Object> roles = new ArrayList<>();

        for(Object aut : grantedAuthorities){
            roles.add(aut);
        }

        String token = Jwts
                .builder()
                .setId("23peopleJWT")
                .setSubject("23people")
                .claim("authorities", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();

        return token;
    }
}
