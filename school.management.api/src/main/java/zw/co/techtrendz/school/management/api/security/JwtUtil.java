package zw.co.techtrendz.school.management.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import zw.co.techtrendz.school.management.api.enums.Role;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expiration = 1000 * 60 * 60; // 1 hour

    public String generateToken(String username, String fingerprint, boolean rememberMe, List<Role> roles) {
        long tokenExpiration = rememberMe ? expiration * 24 * 7 : expiration; // 7 days for "remember me"
        Map<String, Object> claims = new HashMap<>();
        claims.put("fingerprint", fingerprint);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token, String fingerprint) {
        try {
            Claims claims = extractAllClaims(token);
            String tokenFingerprint = claims.get("fingerprint", String.class);
            return tokenFingerprint.equals(fingerprint) && !isTokenExpired(token);
        } catch (Exception e) {
            Logger.getLogger(JwtUtil.class.getName()).log(Level.WARNING, "Token validation failed", e);
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
