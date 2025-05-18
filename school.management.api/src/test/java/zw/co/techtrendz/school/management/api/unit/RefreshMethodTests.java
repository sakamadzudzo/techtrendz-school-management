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
import zw.co.techtrendz.school.management.api.service.impl.AuthServiceImpl;
import zw.co.techtrendz.school.management.api.utility.AppUtils;

class RefreshMethodTests {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Test
    void testRefresh_Success() {
        MockitoAnnotations.openMocks(this);

        String oldToken = "validToken";
        String newToken = "newValidToken";
        String username = "username";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + oldToken);
        when(jwtUtil.validateToken(oldToken, AppUtils.getClientFingerprint(request))).thenReturn(true);
        when(jwtUtil.extractUsername(oldToken)).thenReturn(username);
        when(jwtUtil.generateToken(username, AppUtils.getClientFingerprint(request), true)).thenReturn(newToken);

        ResponseEntity<String> response = authService.refresh(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newToken, response.getBody());
    }

    @Test
    void testRefresh_MissingToken() {
        MockitoAnnotations.openMocks(this);

        when(request.getHeader("Authorization")).thenReturn(null);

        ResponseEntity<String> response = authService.refresh(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Authorization token is missing", response.getBody());
    }

    @Test
    void testRefresh_InvalidToken() {
        MockitoAnnotations.openMocks(this);

        String invalidToken = "invalidToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
        when(jwtUtil.validateToken(invalidToken, AppUtils.getClientFingerprint(request))).thenReturn(false);

        ResponseEntity<String> response = authService.refresh(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token is invalid or expired", response.getBody());
    }
}
