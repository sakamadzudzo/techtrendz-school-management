package zw.co.techtrendz.school.management.api.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zw.co.techtrendz.school.management.api.user.enums.Role;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username, String fingerprint, boolean rememberMe, List<Role> roles) {
        final SecretKey secretKey = new SecretKeySpec(this.secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        final long expirationMs = this.expiration * 60 * 1000;
        long tokenExpiration = rememberMe ? expirationMs * 24 * 7 : expirationMs; // 7 days for "remember me"
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
        final SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
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
