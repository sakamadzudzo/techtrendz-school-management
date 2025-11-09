package zw.co.techtrendz.school.management.api.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import zw.co.techtrendz.school.management.api.user.entity.Person;
import zw.co.techtrendz.school.management.api.user.entity.User;
import zw.co.techtrendz.school.management.api.user.repository.UserRepository;
import zw.co.techtrendz.school.management.api.user.service.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("johndoe@mail.com");
        User user = new User();
        user.setUsername("johndoe");
        user.setPassword("password");
        user.setPerson(person);

        Person person1 = new Person();
        person1.setId(1L);
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setEmail("johndoe@mail.com");
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("johndoe");
        user1.setPassword("password");
        user1.setPerson(person1);

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getPerson().getFirstName());
        assertEquals("Doe", savedUser.getPerson().getLastName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testSaveAllUsers() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setEmail("johndoe@mail.com");
        User user1 = new User();
        user1.setUsername("johndoe");
        user1.setPassword("password");
        user1.setPerson(person1);

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setEmail("janedoe@mail.com");
        User user2 = new User();
        user2.setUsername("janedoe");
        user2.setPassword("password");
        user2.setPerson(person2);

        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        User result_user1 = new User();
        result_user1.setId(1L);
        result_user1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(2L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        User result_user2 = new User();
        result_user2.setId(2L);
        result_user2.setPerson(result_person2);

        List<User> users = Arrays.asList(user1, user2);
        List<User> newUsers = Arrays.asList(result_user1, result_user2);

        when(userRepository.saveAll(users)).thenReturn(newUsers);

        List<User> savedUsers = userService.saveAll(users);

        assertNotNull(savedUsers);
        assertEquals(2, savedUsers.size());
        verify(userRepository, times(1)).save(user1);
        verify(userRepository, times(1)).save(user2);
    }

    @Test
    void testGetUserById() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        User user = new User();
        user.setId(1L);
        user.setPerson(person);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("John", foundUser.getPerson().getFirstName());
        assertEquals("Doe", foundUser.getPerson().getLastName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllUsers() {
        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        User result_user1 = new User();
        result_user1.setId(1L);
        result_user1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(1L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        User result_user2 = new User();
        result_user2.setId(2L);
        result_user2.setPerson(result_person2);

        List<User> users = List.of(result_user1, result_user2);
        Page<User> page = new PageImpl<>(users);

        when(userRepository.findAll(PageRequest.of(0, 10, Sort.by("name").ascending()))).thenReturn(page);

        List<User> foundUsers = userService.getAll(0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        verify(userRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name").ascending()));
    }

    @Test
    void testGetAllUsersByExample() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        User example = new User();
        example.setPerson(person);

        Person result_person1 = new Person();
        result_person1.setId(1L);
        result_person1.setFirstName("John");
        result_person1.setLastName("Doe");
        User result_user1 = new User();
        result_user1.setId(1L);
        result_user1.setPerson(result_person1);

        Person result_person2 = new Person();
        result_person2.setId(1L);
        result_person2.setFirstName("Jane");
        result_person2.setLastName("Doe");
        User result_user2 = new User();
        result_user2.setId(2L);
        result_user2.setPerson(result_person2);

        List<User> users = List.of(result_user1, result_user2);
        Page<User> page = new PageImpl<>(users);

        when(userRepository.findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())))).thenReturn(page);

        List<User> foundUsers = userService.getAllByExample(example, 0, 10, List.of("name"), Sort.Direction.ASC).getContent();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        assertEquals("John", foundUsers.get(0).getPerson().getFirstName());
        assertEquals("Doe", foundUsers.get(0).getPerson().getLastName());
        verify(userRepository, times(1)).findAll(any(Example.class), eq(PageRequest.of(0, 10, Sort.by("name").ascending())));
    }

    @Test
    void testGetByUsername() {
        User user = new User();
        user.setUsername("johndoe");

        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user));

        User foundUser = userService.getByUsername("johndoe");

        assertNotNull(foundUser);
        assertEquals("johndoe", foundUser.getUsername());
        verify(userRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testGetByEmail() {
        Person person = new Person();
        person.setEmail("johndoe@mail.com");
        User user = new User();
        user.setPerson(person);

        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user));

        User foundUser = userService.getByEmail("johndoe@mail.com");

        assertNotNull(foundUser);
        assertEquals("johndoe@mail.com", foundUser.getPerson().getEmail());
        verify(userRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testGetByPhoneNumber() {
        Person person = new Person();
        person.setMobileNumber("123456789");
        User user = new User();
        user.setPerson(person);

        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user));

        User foundUser = userService.getByPhoneNumber("123456789");

        assertNotNull(foundUser);
        assertEquals("123456789", foundUser.getPerson().getMobileNumber());
        verify(userRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testExistsByUsername() {
        User user = new User();
        user.setUsername("johndoe");

        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user));

        boolean exists = userService.existsByUsername("johndoe");

        assertTrue(exists);
        verify(userRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testExistsByEmail() {
        Person person = new Person();
        person.setEmail("johndoe@mail.com");
        User user = new User();
        user.setPerson(person);

        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user));

        boolean exists = userService.existsByEmail("johndoe@mail.com");

        assertTrue(exists);
        verify(userRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testExistsByPhoneNumber() {
        Person person = new Person();
        person.setMobileNumber("123456789");
        User user = new User();
        user.setPerson(person);

        when(userRepository.findOne(any(Specification.class))).thenReturn(Optional.of(user));

        boolean exists = userService.existsByPhoneNumber("123456789");

        assertTrue(exists);
        verify(userRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void testEnableUser() {
        Long userId = 1L;
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        User user = new User();
        user.setId(1L);
        user.setPerson(person);
        user.setEnabled(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.enableUser(userId);

        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testDisableUser() {
        Long userId = 1L;
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        User user = new User();
        user.setId(1L);
        user.setPerson(person);
        user.setEnabled(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.disableUser(userId);

        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testVerifyUser() {
        Long userId = 1L;
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        User user = new User();
        user.setId(1L);
        user.setPerson(person);
        user.setVerified(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.verifyUser(userId);

        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any());
    }
}
