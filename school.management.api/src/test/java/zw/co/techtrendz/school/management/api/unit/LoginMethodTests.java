package zw.co.techtrendz.school.management.api.unit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import zw.co.techtrendz.school.management.api.dto.AuthDto;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.security.JwtUtil;
import zw.co.techtrendz.school.management.api.service.JwtBlacklistService;
import zw.co.techtrendz.school.management.api.service.UserService;
import zw.co.techtrendz.school.management.api.service.impl.AuthServiceImpl;

class LoginMethodTests {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtBlacklistService jwtBlacklistService;

    @Mock
    private UserService userService;

    @Test
    void testLogin_UserNotFound() {
        MockitoAnnotations.openMocks(this);

        AuthDto authDto = new AuthDto();
        authDto.setUsername("nonexistent");
        authDto.setPassword("password");

        when(userService.getByUsername("nonexistent")).thenReturn(null);

        ResponseEntity<String> response = authService.login(authDto, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void testLogin_UserLocked() {
        MockitoAnnotations.openMocks(this);

        AuthDto authDto = new AuthDto();
        authDto.setUsername("lockedUser");
        authDto.setPassword("password");

        User user = new User();
        user.setEnabled(false);

        when(userService.getByUsername("lockedUser")).thenReturn(user);

        ResponseEntity<String> response = authService.login(authDto, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("User is locked", response.getBody());
    }

    @Test
    void testLogin_InvalidPassword() {
        MockitoAnnotations.openMocks(this);

        AuthDto authDto = new AuthDto();
        authDto.setUsername("validUser");
        authDto.setPassword("wrongPassword");

        User user = new User();
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode("correctPassword"));

        when(userService.getByUsername("validUser")).thenReturn(user);

        ResponseEntity<String> response = authService.login(authDto, mock(HttpServletRequest.class));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Invalid password", response.getBody());
    }

    @Test
    void testLogin_Success() {
        MockitoAnnotations.openMocks(this);

        AuthDto authDto = new AuthDto();
        authDto.setUsername("validUser");
        authDto.setPassword("correctPassword");

        User user = new User();
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode("correctPassword"));

        when(userService.getByUsername("validUser")).thenReturn(user);
        when(jwtUtil.generateToken(anyString(), anyString(), anyBoolean(), anyList())).thenReturn("mockToken");

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("mockUserAgent");
        when(request.getHeader("Accept-Language")).thenReturn("mockLanguage");

        ResponseEntity<String> response = authService.login(authDto, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mockToken", response.getBody());
    }
}
