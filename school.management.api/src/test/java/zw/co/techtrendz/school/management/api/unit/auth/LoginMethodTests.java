package zw.co.techtrendz.school.management.api.unit.auth;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import zw.co.techtrendz.school.management.api.auth.dto.AuthDto;
import zw.co.techtrendz.school.management.api.user.entity.User;
import zw.co.techtrendz.school.management.api.common.security.JwtUtil;
import zw.co.techtrendz.school.management.api.common.service.JwtBlacklistService;
import zw.co.techtrendz.school.management.api.user.service.UserService;
import zw.co.techtrendz.school.management.api.auth.service.impl.AuthServiceImpl;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

class LoginMethodTests {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtBlacklistService jwtBlacklistService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_UserNotFound() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("nonexistent");
        authDto.setPassword("password");

        when(userService.getByUsername("nonexistent")).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("User not found", response.getBody().get("error"));
    }

    @Test
    void testLogin_UserLocked() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("lockedUser");
        authDto.setPassword("password");

        User user = new User();
        user.setEnabled(false);

        when(userService.getByUsername("lockedUser")).thenReturn(user);

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("User is locked", response.getBody().get("error"));
    }

    @Test
    void testLogin_InvalidPassword() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("validUser");
        authDto.setPassword("wrongPassword");

        User user = new User();
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode("correctPassword"));

        when(userService.getByUsername("validUser")).thenReturn(user);

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Invalid password", response.getBody().get("error"));
    }

    @Test
    void testLogin_Success() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("validUser");
        authDto.setPassword("correctPassword");
        authDto.setRememberMe(false);

        User user = new User();
        user.setEnabled(true);
        user.setUsername("validUser");
        user.setPassword(new BCryptPasswordEncoder().encode("correctPassword"));

        when(userService.getByUsername("validUser")).thenReturn(user);
        when(jwtUtil.generateToken(anyString(), anyString(), anyBoolean(), any())).thenReturn("mockToken");

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("mockUserAgent");
        when(request.getHeader("Accept-Language")).thenReturn("mockLanguage");

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, request);
        Logger.getLogger(LoginMethodTests.class.getName()).log(Level.INFO, "Login response: {0}", response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockToken", response.getBody().get("token"));
    }
}
