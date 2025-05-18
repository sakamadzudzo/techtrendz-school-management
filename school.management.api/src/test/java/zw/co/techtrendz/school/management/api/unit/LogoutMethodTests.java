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
import zw.co.techtrendz.school.management.api.security.JwtUtil;
import zw.co.techtrendz.school.management.api.service.JwtBlacklistService;
import zw.co.techtrendz.school.management.api.service.impl.AuthServiceImpl;
import zw.co.techtrendz.school.management.api.utility.AppUtils;

class LogoutMethodTests {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtBlacklistService jwtBlacklistService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Test
    void testLogout_Success() {
        MockitoAnnotations.openMocks(this);

        String token = "validToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.validateToken(token, AppUtils.getClientFingerprint(mock(HttpServletRequest.class)))).thenReturn(true);

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged out successfully", response.getBody());
        verify(jwtBlacklistService, times(1)).blacklistToken(token);
    }

    @Test
    void testLogout_MissingToken() {
        MockitoAnnotations.openMocks(this);

        when(request.getHeader("Authorization")).thenReturn(null);

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Authorization token is missing", response.getBody());
        verify(jwtBlacklistService, never()).blacklistToken(anyString());
    }

    @Test
    void testLogout_InvalidToken() {
        MockitoAnnotations.openMocks(this);

        String invalidToken = "invalidToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
        when(jwtUtil.validateToken(invalidToken, AppUtils.getClientFingerprint(mock(HttpServletRequest.class)))).thenReturn(false);

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token is invalid", response.getBody());
    }

    @Test
    void testLogout_InvalidTokenFormat() {
        MockitoAnnotations.openMocks(this);

        String invalidToken = "invalidToken";
        when(request.getHeader("Authorization")).thenReturn(invalidToken);

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid token format", response.getBody());
    }
}
