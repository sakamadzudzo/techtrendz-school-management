package zw.co.techtrendz.school.management.api.unit.auth;

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
import zw.co.techtrendz.school.management.api.user.entity.Person;
import zw.co.techtrendz.school.management.api.user.enums.Role;
import zw.co.techtrendz.school.management.api.common.security.JwtUtil;
import zw.co.techtrendz.school.management.api.auth.service.impl.AuthServiceImpl;
import zw.co.techtrendz.school.management.api.common.service.JwtBlacklistService;
import zw.co.techtrendz.school.management.api.user.service.PersonService;
import zw.co.techtrendz.school.management.api.user.service.UserService;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthServiceTests {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtBlacklistService jwtBlacklistService;

    @Mock
    private UserService userService;

    @Mock
    private PersonService personService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginWithNullAuthDto() {
        ResponseEntity<Map<String, Object>> response = authService.login(null, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Username is required", response.getBody().get("error"));
    }

    @Test
    void testLoginWithNullUsername() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername(null);

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Username is required", response.getBody().get("error"));
    }

    @Test
    void testLoginWithBlankUsername() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("");

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Username is required", response.getBody().get("error"));
    }

    @Test
    void testLoginWithUserNotFound() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("testuser");
        authDto.setPassword("password");

        when(userService.getByUsername("testuser")).thenReturn(null);

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("User not found", response.getBody().get("error"));
    }

    @Test
    void testLoginWithDisabledUser() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("testuser");
        authDto.setPassword("password");

        User user = new User();
        user.setEnabled(false);

        when(userService.getByUsername("testuser")).thenReturn(user);

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("User is locked", response.getBody().get("error"));
    }

    @Test
    void testLoginWithInvalidPassword() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("testuser");
        authDto.setPassword("wrongpassword");

        User user = new User();
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode("correctpassword"));
        user.setRoles(Arrays.asList(Role.STUDENT));

        when(userService.getByUsername("testuser")).thenReturn(user);

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Invalid password", response.getBody().get("error"));
    }

    @Test
    void testLoginSuccess() {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("testuser");
        authDto.setPassword("password");
        authDto.setRememberMe(true);

        User user = new User();
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode("password"));
        user.setRoles(Arrays.asList(Role.STUDENT));

        when(userService.getByUsername("testuser")).thenReturn(user);
        when(jwtUtil.generateToken(anyString(), anyString(), anyBoolean(), anyList())).thenReturn("jwt-token");

        ResponseEntity<Map<String, Object>> response = authService.login(authDto, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("token"));
        assertTrue(response.getBody().containsKey("user"));
        assertEquals("jwt-token", response.getBody().get("token"));
        verify(jwtUtil, times(1)).generateToken(eq("testuser"), anyString(), eq(true), anyList());
    }

    @Test
    void testLogoutWithMissingAuthorizationHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Authorization token is missing", response.getBody());
    }

    @Test
    void testLogoutWithEmptyAuthorizationHeader() {
        when(request.getHeader("Authorization")).thenReturn("");

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Authorization token is missing", response.getBody());
    }

    @Test
    void testLogoutWithInvalidTokenFormat() {
        when(request.getHeader("Authorization")).thenReturn("InvalidFormat");

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid token format", response.getBody());
    }

    @Test
    void testLogoutWithInvalidToken() {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(false);

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token is invalid", response.getBody());
    }

    @Test
    void testLogoutSuccess() {
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(true);

        ResponseEntity<String> response = authService.logout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged out successfully", response.getBody());
        verify(jwtBlacklistService, times(1)).blacklistToken("valid-token");
    }

    @Test
    void testRefreshWithMissingAuthorizationHeader() {
        when(request.getHeader("Authorization")).thenReturn(null);

        ResponseEntity<String> response = authService.refresh(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Authorization token is missing", response.getBody());
    }

    @Test
    void testRefreshWithInvalidTokenFormat() {
        when(request.getHeader("Authorization")).thenReturn("InvalidFormat");

        ResponseEntity<String> response = authService.refresh(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid token format", response.getBody());
    }

    @Test
    void testRefreshWithInvalidToken() {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(false);

        ResponseEntity<String> response = authService.refresh(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token is invalid or expired", response.getBody());
    }

    @Test
    void testRefreshSuccess() {
        User user = new User();
        user.setRoles(Arrays.asList(Role.STUDENT));

        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtUtil.validateToken(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.extractUsername("valid-token")).thenReturn("testuser");
        when(userService.getByUsername("testuser")).thenReturn(user);
        when(jwtUtil.generateToken(anyString(), anyString(), anyBoolean(), anyList())).thenReturn("new-jwt-token");

        ResponseEntity<String> response = authService.refresh(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("new-jwt-token", response.getBody());
        verify(jwtUtil, times(1)).generateToken(eq("testuser"), anyString(), eq(true), anyList());
    }

    @Test
    void testRegisterSuccess() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        Person person = new Person();
        person.setEmail("test@example.com");
        person.setFirstName("Test");
        person.setLastName("User");
        user.setPerson(person);

        when(userService.existsByEmail("test@example.com", null)).thenReturn(false);
        when(userService.existsByUsername("testuser", null)).thenReturn(false);

        ResponseEntity<String> response = authService.register(user, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(userService, times(1)).save(user);
    }

    @Test
    void testRegisterWithExistingPerson() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        Person person = new Person();
        person.setId(1L);
        person.setEmail("test@example.com");
        person.setFirstName("Test");
        person.setLastName("User");
        user.setPerson(person);

        when(personService.getById(1L)).thenReturn(person);
        when(userService.existsByEmail("test@example.com", null)).thenReturn(false);
        when(userService.existsByUsername("testuser", null)).thenReturn(false);

        ResponseEntity<String> response = authService.register(user, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(personService, times(1)).getById(1L);
        verify(userService, times(1)).save(user);
    }
}