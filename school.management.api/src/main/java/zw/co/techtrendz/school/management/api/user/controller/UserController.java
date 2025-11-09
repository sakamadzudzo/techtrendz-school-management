package zw.co.techtrendz.school.management.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.auth.dto.PasswordDto;
import zw.co.techtrendz.school.management.api.user.entity.User;
import zw.co.techtrendz.school.management.api.user.service.UserService;
import zw.co.techtrendz.school.management.api.user.view.UserDto;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Management of system users and authentication")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Save user",
            description = "Creates or updates a user account"
    )
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public User saveUser(@RequestBody @Valid User user) {
        return this.userService.save(user);
    }

    @Operation(
            summary = "Save multiple users",
            description = "Creates or updates multiple user accounts"
    )
    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> saveAllUsers(@RequestBody @Valid List<User> users) {
        return this.userService.saveAll(users);
    }

    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a specific user by their ID"
    )
    @GetMapping("/get/one")
    public User getUser(@Parameter(description = "User ID") @RequestParam Long id) {
        return this.userService.getById(id);
    }

    @Operation(
            summary = "Get all users",
            description = "Retrieves all users with pagination and sorting"
    )
    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAllUsers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.userService.getAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get users by example",
            description = "Retrieves users matching the provided example with pagination and sorting"
    )
    @GetMapping("/get/all/by/example")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAllUsersByExample(
            @RequestBody @Valid User user,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.userService.getAllByExample(user, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get users by filter",
            description = "Retrieves users matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/get/all/by/filter")
    public Page<User> getAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.userService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get user view by ID",
            description = "Retrieves a detailed view of a specific user"
    )
    @GetMapping("/view/get/one")
    public UserDto getViewUser(@Parameter(description = "User ID") @RequestParam Long id) {
        return this.userService.getViewById(id);
    }

    @Operation(
            summary = "Get all user views",
            description = "Retrieves detailed views of all users with pagination and sorting"
    )
    @GetMapping("/view/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDto> getViewAllUsers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.userService.getViewAll(page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get user views by example",
            description = "Retrieves detailed views of users matching the provided example with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/example")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDto> getViewAllUsersByExample(
            @RequestBody @Valid User user,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.userService.getViewAllByExample(user, page, size, sortBy, sortDir);
    }

    @Operation(
            summary = "Get user views by filter",
            description = "Retrieves detailed views of users matching the provided filter text with pagination and sorting"
    )
    @GetMapping("/view/get/all/by/filter")
    public Page<UserDto> getViewAllClassroomsByFilter(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "25") int size,
            @Parameter(description = "Sort fields") @RequestParam(defaultValue = "name") List<String> sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
            @Parameter(description = "Filter text") @RequestParam(defaultValue = "", required = false) String filter) {
        return this.userService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @Operation(
            summary = "Get user by username",
            description = "Retrieves a user by their username"
    )
    @GetMapping("/get/by/username")
    public User getUserByUsername(@Parameter(description = "Username") @RequestParam String username) {
        return this.userService.getByUsername(username);
    }

    @Operation(
            summary = "Get user by email",
            description = "Retrieves a user by their email address"
    )
    @GetMapping("/get/by/email")
    public User getUserByEmail(@Parameter(description = "Email address") @RequestParam String email) {
        return this.userService.getByEmail(email);
    }

    @Operation(
            summary = "Get user by phone",
            description = "Retrieves a user by their phone number"
    )
    @GetMapping("/get/by/phone")
    public User getUserByPhone(@Parameter(description = "Phone number") @RequestParam String phone) {
        return this.userService.getByPhoneNumber(phone);
    }

    @Operation(
            summary = "Check if username exists",
            description = "Checks if a username already exists in the system"
    )
    @GetMapping("/get/exists/by/username")
    public boolean getUserExistsByUsername(@Parameter(description = "Username") @RequestParam String username) {
        return this.userService.existsByUsername(username);
    }

    @Operation(
            summary = "Check if email exists",
            description = "Checks if an email address already exists in the system"
    )
    @GetMapping("/get/exists/by/email")
    public boolean getUserExistsByEmail(@Parameter(description = "Email address") @RequestParam String email) {
        return this.userService.existsByEmail(email);
    }

    @Operation(
            summary = "Check if phone exists",
            description = "Checks if a phone number already exists in the system"
    )
    @GetMapping("/get/exists/by/phone")
    public boolean getUserExistsByPhone(@Parameter(description = "Phone number") @RequestParam String phone) {
        return this.userService.existsByPhoneNumber(phone);
    }

    @Operation(
            summary = "Change password",
            description = "Changes the password for the current user"
    )
    @PostMapping("/change-password")
    public void changePassword(@RequestBody PasswordDto passwordDto, HttpServletRequest request) {
        this.userService.changePassword(passwordDto.getCurrentPassword(), passwordDto.getNewPassword(), request);
    }
}
