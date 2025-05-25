package zw.co.techtrendz.school.management.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.dto.AuthDto;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.security.JwtUtil;
import zw.co.techtrendz.school.management.api.service.AuthService;
import zw.co.techtrendz.school.management.api.service.JwtBlacklistService;
import zw.co.techtrendz.school.management.api.service.UserService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtBlacklistService jwtBlacklistService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid AuthDto authDto, HttpServletRequest request) {
        return this.authService.login(authDto, request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return this.authService.logout(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request) {
        return this.authService.refresh(request);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid User user, HttpServletRequest request) {
        return this.authService.register(user, request);
    }
}