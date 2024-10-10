package com.example.bookStoreServicesApp.util;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bookStoreServicesApp.exception.generateTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService
{
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    public static String generateToken(String role, Long id){
        Algorithm algorithm = Algorithm.HMAC256("My_Secret_Key");
        String token = JWT.create()
                .withIssuer("Team-2")
                .withClaim("role", role)
                .withClaim("id",id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30000000L))
                .withJWTId(String.valueOf(UUID.randomUUID()))
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(algorithm);
        return token;
    }


    public String generateRole(String token) throws generateTokenException {
        try {
            String secretKeyString = "My_Secret_Key";
            Algorithm algorithm = Algorithm.HMAC256(secretKeyString);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Team-2")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token.replace("Bearer ", ""));
            String role = decodedJWT.getClaim("role").asString();
            return role;
        }
        catch(Exception e){
            throw new generateTokenException("Token didn't generate");
        }
    }
    public Long generateID(String token) {
        String secretKeyString = "My_Secret_Key";
        Algorithm algorithm = Algorithm.HMAC256(secretKeyString);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("Team-2")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token.replace("Bearer ", ""));
        Long role = decodedJWT.getClaim("id").asLong();
        return role;
    }


}