package zw.co.techtrendz.school.management.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.techtrendz.school.management.api.dto.AuthDto;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.security.JwtUtil;
import zw.co.techtrendz.school.management.api.service.AuthService;
import zw.co.techtrendz.school.management.api.service.JwtBlacklistService;
import zw.co.techtrendz.school.management.api.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User authentication and authorization endpoints")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final JwtBlacklistService jwtBlacklistService;
    private final UserService userService;
    private final AuthService authService;

    public AuthController(JwtUtil jwtUtil, JwtBlacklistService jwtBlacklistService, UserService userService, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.jwtBlacklistService = jwtBlacklistService;
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns JWT tokens"
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid AuthDto authDto, HttpServletRequest request) {
        return this.authService.login(authDto, request);
    }

    @Operation(
            summary = "User logout",
            description = "Logs out a user and blacklists the JWT token"
    )
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return this.authService.logout(request);
    }

    @Operation(
            summary = "Refresh token",
            description = "Refreshes an existing JWT token"
    )
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request) {
        return this.authService.refresh(request);
    }

    @Operation(
            summary = "User registration",
            description = "Registers a new user account"
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid User user, HttpServletRequest request) {
        return this.authService.register(user, request);
    }
}