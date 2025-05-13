package com.assignment.gateway.utils;

import com.assignment.core.utils.SimpleLogger;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtils {

    @Value("${jwt.secretKey}")
    private String secret;

    private static final String USERNAME = "userName";
    private static final String USER_ID = "userId";
    private static Claims getClaimSFromToken(String secret, String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (MalformedJwtException ex) {
            SimpleLogger.error("Invalid JWT token.");
            throw ex;
        } catch (ExpiredJwtException ex) {
            SimpleLogger.error("Expired JWT token.");
            throw  ex;
        } catch (UnsupportedJwtException ex) {
            SimpleLogger.error("Unsupported JWT token.");
            throw ex;
        } catch (IllegalArgumentException ex) {
            SimpleLogger.error("JWT claims string is empty.");
            throw ex;
        }

    }


    public  String getJwtFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getOrEmpty("Authorization").get(0);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        SimpleLogger.warn("JWT Token does not begin with Bearer String");
        throw new JwtException("JWT Token does not begin with Bearer String");
    }

    public  String getUsernameFromRequest(ServerHttpRequest request) {
        String token = getJwtFromRequest(request);
        return getUserNameFromToken(token);
    }

    public  String getUserNameFromToken(String token) {
        Claims claims = getClaimSFromToken(secret, token);
        return claims.get(USERNAME).toString();
    }

    public  String getUserIdFromToken(String token) {
        Claims claims = getClaimSFromToken(secret, token);
        return claims.get(USER_ID).toString();
    }


    public  String getUserIdFromRequest(ServerHttpRequest request) {
        String token = getJwtFromRequest(request);
        return getUserIdFromToken(token);
    }


}