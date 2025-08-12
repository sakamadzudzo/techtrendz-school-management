package zw.co.techtrendz.school.management.api.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.AuthDto;
import zw.co.techtrendz.school.management.api.dto.UserViewDto;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.security.JwtUtil;
import zw.co.techtrendz.school.management.api.service.AuthService;
import zw.co.techtrendz.school.management.api.service.JwtBlacklistService;
import zw.co.techtrendz.school.management.api.service.PersonService;
import zw.co.techtrendz.school.management.api.service.UserService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtBlacklistService jwtBlacklistService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;


    @Override
    public ResponseEntity<Map<String, Object>> login(AuthDto authDto, HttpServletRequest request) {
        String fingerprint = AppUtils.getClientFingerprint(request);
        User user = this.userService.getByUsername(authDto.getUsername());

        if (user == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } else if (!user.getEnabled()) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "User is locked");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } else {
            if (!new BCryptPasswordEncoder().matches(authDto.getPassword(), user.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("error", "Invalid password");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        }

        String token = this.jwtUtil.generateToken(authDto.getUsername(), fingerprint, authDto.isRememberMe(), user.getRoles());
        Map<String, Object> principal = new HashMap<>();
        principal.put("token", token);
        principal.put("user", AppUtils.userToDto(user));
        return ResponseEntity.ok(principal);
    }

    @Override
    public ResponseEntity<String> logout(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization token is missing");
        } else if (!request.getHeader("Authorization").startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format");
        } else {
            String token = request.getHeader("Authorization").substring(7);
            if (!this.jwtUtil.validateToken(token, AppUtils.getClientFingerprint(request))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is invalid");
            } else {
                this.jwtBlacklistService.blacklistToken(token);
                return ResponseEntity.ok("Logged out successfully");
            }
        }
    }

    @Override
    public ResponseEntity<String> refresh(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null || request.getHeader("Authorization").isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization token is missing");
        } else if (!request.getHeader("Authorization").startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format");
        } else {
            String token = request.getHeader("Authorization").substring(7);
            if (!this.jwtUtil.validateToken(token, AppUtils.getClientFingerprint(request))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token is invalid or expired");
            } else {
                String username = this.jwtUtil.extractUsername(token);
                User user = this.userService.getByUsername(username);
                String fingerprint = AppUtils.getClientFingerprint(request);
                String newToken = this.jwtUtil.generateToken(username, fingerprint, true, user.getRoles());
                return ResponseEntity.ok(newToken);
            }
        }
    }

    @Override
    public ResponseEntity<String> register(User user, HttpServletRequest request) {
        if (user.getPerson() != null && user.getPerson().getId() != null && !user.getPerson().getId().equals(0L)) {
            user.setPerson(this.personService.getById(user.getPerson().getId()));
        }
        AppUtils.validateUser(user, this.userService);
        this.userService.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
