package org.store.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;


    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

    public String getUsername(String token) {
        return this.getAllClaimsFromToken(token).getSubject();
    }

    private boolean isTokenExpired (String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
