package com.brytvich.helpdesk.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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
    public String generateTokenWithRole(String username,String role){
        Date expiredDate = Date.from(ZonedDateTime.now().plusMinutes(120).toInstant());
        return JWT.create()
                .withSubject("UserDetails")
                .withClaim("username",username)
                .withClaim("role",role)
                .withIssuedAt(new Date())
                .withIssuer("brytvich")
                .withExpiresAt(expiredDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("brytvich")
                .withSubject("UserDetails")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
