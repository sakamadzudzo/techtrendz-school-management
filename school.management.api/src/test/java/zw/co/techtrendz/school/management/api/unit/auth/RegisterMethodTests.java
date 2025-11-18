package zw.co.techtrendz.school.management.api.unit.auth;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import zw.co.techtrendz.school.management.api.user.entity.Person;
import zw.co.techtrendz.school.management.api.user.entity.User;
import zw.co.techtrendz.school.management.api.user.enums.Role;
import zw.co.techtrendz.school.management.api.user.enums.Salutation;
import zw.co.techtrendz.school.management.api.common.exception.UserCreationException;
import zw.co.techtrendz.school.management.api.user.service.PersonService;
import zw.co.techtrendz.school.management.api.user.service.UserService;
import zw.co.techtrendz.school.management.api.auth.service.impl.AuthServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;

class RegisterMethodTests {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserService userService;

    @Mock
    private PersonService personService;

    @Mock
    private HttpServletRequest request;

    private User user = new User();
    private User u1 = new User();
    private Person p1 = new Person();

    @Test
    void testRegister_Success() {
        MockitoAnnotations.openMocks(this);
        this.setup();

        when(personService.getById(1L)).thenReturn(p1);
        when(userService.save(u1)).thenReturn(user);

        ResponseEntity<String> response = authService.register(u1, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
        verify(userService, times(1)).save(u1);
    }

    @Test
    void testRegister_UserAlreadyExists() {
        MockitoAnnotations.openMocks(this);
        this.setup();
        when(userService.existsByUsername(u1.getUsername())).thenReturn(true);
        when(userService.existsByEmail(u1.getPerson().getEmail())).thenReturn(false);
        when(personService.getById(1L)).thenReturn(p1);

        Exception exception = assertThrows(UserCreationException.class, () -> authService.register(u1, request));

        assertEquals("Username already in use", exception.getMessage());
        verify(userService, never()).save(u1);
    }

    @Test
    void testRegister_InvalidUsername() {
        MockitoAnnotations.openMocks(this);
        this.setup();
        u1.setUsername("");

        Exception exception = assertThrows(UserCreationException.class, () -> authService.register(u1, request));

        assertEquals("Username cannot be null or empty", exception.getMessage());
        verify(userService, never()).save(u1);
    }

    @Test
    void testRegister_MissingUsername() {
        MockitoAnnotations.openMocks(this);
        this.setup();
        u1.setUsername(null);

        Exception exception = assertThrows(UserCreationException.class, () -> authService.register(u1, request));

        assertEquals("Username cannot be null or empty", exception.getMessage());
        verify(userService, never()).save(user);
    }

    @Test
    void testRegister_MissingPassword() {
        MockitoAnnotations.openMocks(this);
        this.setup();
        u1.setPassword(null);

        Exception exception = assertThrows(UserCreationException.class, () -> authService.register(u1, request));

        assertEquals("Password cannot be null or empty", exception.getMessage());
        verify(userService, never()).save(user);
    }

    private void setup() {
        p1.setId(1L);
        p1.setSalutation(Salutation.MR);
        p1.setFirstName("Arlington");
        p1.setOtherNames("Chikozho");
        p1.setLastName("Maridimuka");
        p1.setEmail("amaridimuka@mail.com");
        p1.setMobileNumber("0771234567");
        p1.setMobileNumber2("0771234568");
        p1.setTelNumber("08600230056");
        p1.setWhatsapp("0771234567");
        p1.setFacebook("https://www.facebook.com/arlington.maridimuka");
        p1.setLinkedin("https://www.linkedin.com/in/arlington-maridimuka-0b1a4a1b/");
        p1.setDateOfBirth(LocalDate.of(1972,7,8));

        u1.setUsername("amaridimuka");
        u1.setPassword("password");
        u1.setPerson(p1);
        u1.setRoles(Arrays.asList(Role.TEACHER));
        u1.setEnabled(true);
        u1.setEnabled(true);

        user.setId(1L);
        user.setUsername("amaridimuka");
        user.setPassword("password");
        user.setPerson(p1);
        user.setRoles(Arrays.asList(Role.TEACHER));
        user.setEnabled(true);
        user.setEnabled(true);
    }
}
