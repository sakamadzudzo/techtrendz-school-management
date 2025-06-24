package zw.co.techtrendz.school.management.api.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import zw.co.techtrendz.school.management.api.enums.Role;
import zw.co.techtrendz.school.management.api.service.JwtBlacklistService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String fingerprint = AppUtils.getClientFingerprint(request);

                if (this.jwtBlacklistService.isTokenBlacklisted(token)) {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                    response.getWriter().write("Token is blacklisted");
                    Logger.getLogger(JwtAuthenticationFilter.class.getName()).log(Level.WARNING, "Token is blacklisted");
                    return;
                } else if (this.jwtUtil.validateToken(token, fingerprint)) {
                    String username = this.jwtUtil.extractUsername(token);
                    Claims claims = this.jwtUtil.extractAllClaims(token);
                    List<String> roles = (List<String>) claims.get("roles", List.class);
                    List<SimpleGrantedAuthority> authorities = roles
                            .stream()
                            .map(role ->
                                    new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
                            )
                            .toList();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                    response.getWriter().write("Token validation failed");
                    Logger.getLogger(JwtAuthenticationFilter.class.getName()).log(Level.WARNING, "Token validation failed");
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            if (response.getStatus() == HttpServletResponse.SC_OK) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Default to 500 if no status is set
            }
            response.getWriter().write("Unexpected error occurred");
            Logger.getLogger(JwtAuthenticationFilter.class.getName()).log(Level.SEVERE, "Unexpected error occurred", e);
        }
    }
}