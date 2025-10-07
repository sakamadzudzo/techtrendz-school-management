package zw.co.techtrendz.school.management.api.security;

import zw.co.techtrendz.school.management.api.service.JwtBlacklistService;
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
import zw.co.techtrendz.school.management.api.utility.AppUtils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtBlacklistService jwtBlacklistService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, JwtBlacklistService jwtBlacklistService) {
        this.jwtUtil = jwtUtil;
        this.jwtBlacklistService = jwtBlacklistService;
    }

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
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token is blacklisted\"}");
                    response.getWriter().flush();
                    Logger.getLogger(JwtAuthenticationFilter.class.getName()).log(Level.WARNING, "Token is blacklisted");
                    return; // Stop further processing
                } else if (this.jwtUtil.validateToken(token, fingerprint)) {
                    String username = this.jwtUtil.extractUsername(token);
                    Claims claims = this.jwtUtil.extractAllClaims(token);
                    List<String> roles = (List<String>) claims.get("roles", List.class);
                    List<SimpleGrantedAuthority> authorities = roles
                            .parallelStream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                            .toList();

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token validation failed\"}");
                    response.getWriter().flush();
                    Logger.getLogger(JwtAuthenticationFilter.class.getName()).log(Level.WARNING, "Token validation failed");
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            if (response.getStatus() == HttpServletResponse.SC_OK) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unexpected error occurred\"}");
            response.getWriter().flush();
            Logger.getLogger(JwtAuthenticationFilter.class.getName()).log(Level.SEVERE, "Unexpected error occurred", e);
        }
    }
}
