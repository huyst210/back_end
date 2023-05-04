package com.ecommerce.bonuongbackend.security;

import com.ecommerce.bonuongbackend.model.InactivatedUser;
import com.ecommerce.bonuongbackend.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    public String generateToken(String secret, String userId) {
//        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 691200000L);
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(Charset.forName("UTF-8")))
                .compact();
    }

    public String getUserIdFromJWT(String secret, String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret.getBytes(Charset.forName("UTF-8")))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String secret, String authToken) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes(Charset.forName("UTF-8"))).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public String generateTokenRegister(String secret, InactivatedUser user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 600000L))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(Charset.forName("UTF-8")))
                .compact();
    }

    public String generateTokenResetPassword(String secret, User user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 600000L))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(Charset.forName("UTF-8")))
                .compact();
    }

    public String generateTokenPayment(String secret, String orderId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(orderId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 691200000L))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(Charset.forName("UTF-8")))
                .compact();
    }
}
