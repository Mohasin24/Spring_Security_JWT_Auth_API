package com.authentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;


@Slf4j
public class JwtUtils
{
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    // Don't allow for Object creation of the JwtUtils class it should be read only
    private JwtUtils(){}
    public static boolean validateToken(String jwtToken){
        return (parseToken(jwtToken) != null);
    }

    public static Claims parseToken(String jwtToken){

        var jwtParser  = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build();

        try {
            return  jwtParser.parseSignedClaims(jwtToken).getPayload();
        }catch (Exception e){
            log.error("JWT Exception occurred");
        }

        return null;
    }
    public static String getUsernameFromToken(String jwtToken){

        Claims claims = parseToken(jwtToken);

        if(claims != null){
            return claims.getSubject();
        }

        return null;
    }
}
