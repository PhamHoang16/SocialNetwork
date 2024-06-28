package com.example.userservice.security.jwt;

import com.example.userservice.security.userprinciple.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "2o55pzkbrU2jCQOyIDOFyg1uacsahtsxcghk2Eotsafsaf";
    private int jwtExpiration = 86400000;
    public String createToken(Authentication authentication) {
        UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {}", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid format token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }
    public String getUserNameFromToken(String token) {
        String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody().getSubject();
        return username;
    }
}
