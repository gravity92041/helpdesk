package com.brytvich.helpdesk.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String username){
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(120).toInstant());

        return JWT.create()
                .withSubject("UserDetails")
                .withClaim("username",username)
                .withIssuedAt(new Date())
                .withIssuer("brytvich")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

}
