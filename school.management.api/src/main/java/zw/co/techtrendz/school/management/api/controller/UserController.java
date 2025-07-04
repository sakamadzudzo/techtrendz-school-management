package zw.co.techtrendz.school.management.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.service.UserService;
import zw.co.techtrendz.school.management.api.view.UserDto;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public User saveUser(@RequestBody @Valid User user) {
        return this.userService.save(user);
    }

    @PostMapping("/save/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> saveAllUsers(@RequestBody @Valid List<User> users) {
        return this.userService.saveAll(users);
    }

    @GetMapping("/get/one")
    public User getUser(@RequestParam Long id) {
        return this.userService.getById(id);
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.userService.getAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/example")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAllUsersByExample(@RequestBody @Valid User user, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.userService.getAllByExample(user, page, size, sortBy, sortDir);
    }

    @GetMapping("/get/all/by/filter")
    public Page<User> getAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.userService.getAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/view/get/one")
    public UserDto getViewUser(@RequestParam Long id) {
        return this.userService.getViewById(id);
    }

    @GetMapping("/view/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDto> getViewAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.userService.getViewAll(page, size, sortBy, sortDir);
    }

    @GetMapping("/view/get/all/by/example")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDto> getViewAllUsersByExample(@RequestBody @Valid User user, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir) {
        return this.userService.getViewAllByExample(user, page, size, sortBy, sortDir);
    }

    @GetMapping("/view/get/all/by/filter")
    public Page<UserDto> getViewAllClassroomsByFilter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int size, @RequestParam(defaultValue = "name") List<String> sortBy, @RequestParam(defaultValue = "ASC") Sort.Direction sortDir, @RequestParam(defaultValue = "", required = false) String filter) {
        return this.userService.getViewAllByFilter(page, size, sortBy, sortDir, filter);
    }

    @GetMapping("/get/by/username")
    public User getUserByUsername(@RequestParam String username) {
        return this.userService.getByUsername(username);
    }

    @GetMapping("/get/by/email")
    public User getUserByEmail(@RequestParam String email) {
        return this.userService.getByEmail(email);
    }

    @GetMapping("/get/by/phone")
    public User getUserByPhone(@RequestParam String phone) {
        return this.userService.getByPhoneNumber(phone);
    }

    @GetMapping("/get/exists/by/username")
    public boolean getUserExistsByUsername(@RequestParam String username) {
        return this.userService.existsByUsername(username);
    }

    @GetMapping("/get/exists/by/email")
    public boolean getUserExistsByEmail(@RequestParam String email) {
        return this.userService.existsByEmail(email);
    }

    @GetMapping("/get/exists/by/phone")
    public boolean getUserExistsByPhone(@RequestParam String phone) {
        return this.userService.existsByPhoneNumber(phone);
    }
}
