package zw.co.techtrendz.school.management.api.user.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import zw.co.techtrendz.school.management.api.user.entity.User;
import zw.co.techtrendz.school.management.api.user.querydsl.UserQueryService;
import zw.co.techtrendz.school.management.api.user.repository.UserRepository;
import zw.co.techtrendz.school.management.api.common.security.JwtUtil;
import zw.co.techtrendz.school.management.api.user.service.PersonService;
import zw.co.techtrendz.school.management.api.user.service.UserService;
import zw.co.techtrendz.school.management.api.common.utility.AppUtils;
import zw.co.techtrendz.school.management.api.common.utility.QuerydslUtils;
import zw.co.techtrendz.school.management.api.common.utility.SpecificationUtils;
import zw.co.techtrendz.school.management.api.user.view.UserDto;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonService personService;
    private final UserQueryService userQueryService;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PersonService personService, UserQueryService userQueryService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.personService = personService;
        this.userQueryService = userQueryService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User save(User user) {
        if (user.getPerson() != null && user.getPerson().getId() != null && !user.getPerson().getId().equals(0L)) {
            user.setPerson(this.personService.getById(user.getPerson().getId()));
        } else if (user.getPerson() != null && (user.getPerson().getId() == null || user.getPerson().getId().equals(0L))) {
            user.setPerson(this.personService.save(user.getPerson()));
        }
        AppUtils.validateUser(user, this);

        if (user.getId() != null && !user.getId().equals(0)) {
            User existingUser = this.userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null) {
                user.setPassword(existingUser.getPassword()); // Keep the existing password
                user.setEnabled(existingUser.getEnabled()); // Keep the existing enabled status
            }
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); // Encrypt password
            user.setEnabled(false); // User is disabled until email is verified
        }

        User savedUser = this.userRepository.save(user);

        // Generate and send verification code
        String verificationCode = UUID.randomUUID().toString();
        final Map<String, String> verificationCodes = new HashMap<>();
        verificationCodes.put(user.getPerson().getEmail(), verificationCode);
//        emailService.sendVerificationEmail(user.getEmail(), verificationCode);

        return savedUser;
    }

    @Override
    public List<User> saveAll(List<User> users) {
        return users.parallelStream().map(this::save).toList();
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("person.name");
        }
        String[] sortFields = sortBy.toArray(new String[sortBy.size()]);
        return this.userRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
    }

    @Override
    public Page<User> getAllByExample(User user, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (user == null) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Example<User> example = Example.of(user);

            if (sortBy.isEmpty()) {
                sortBy.add("person.name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.userRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public Page<User> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getAll(page, size, sortBy, sortDir);
        } else {
            Specification<User> spec = Specification.where(null);
            for (var field : User.class.getDeclaredFields()) {
                field.setAccessible(true); // Ensure private fields are accessible
                if (field.getType().equals(String.class)) {
                    spec = spec.or(SpecificationUtils.likeMatch(field.getName(), filter));
                }
            }

            if (sortBy.isEmpty()) {
                sortBy.add("person.name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.userRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public UserDto getViewById(Long id) {
        UserDto userDto = this.userQueryService.getUserDetails(id);
        if (userDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id '%d' was not found", id));
        }
        return userDto;
    }

    @Override
    public Page<UserDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("person.name");
        }
        return this.userQueryService.getAllUserDetails(page, size, sortBy, sortDir);
    }

    @Override
    public Page<UserDto> getViewAllByExample(User user, int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (user == null) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            Example<User> example = Example.of(user);

            if (sortBy.isEmpty()) {
                sortBy.add("person.name");
            }

            return this.userQueryService.getAllUserDetailsByExample(page, size, sortBy, sortDir, example);
        }
    }

    @Override
    public Page<UserDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter) {
        if (filter == null || filter.isBlank()) {
            return this.getViewAll(page, size, sortBy, sortDir);
        } else {
            BooleanExpression predicate = null;
            predicate = AppUtils.getBooleanExpression(filter, predicate, User.class, "user", Arrays.asList("password", "roles"));

            if (sortBy.isEmpty()) {
                sortBy.add("person.name");
            }

            return this.userQueryService.getAllUserDetailsByPredicate(page, size, sortBy, sortDir, predicate);
        }
    }

    @Override
    public User getByUsername(String username) {
//        Specification<User> spec = Specification.where(
//                SpecificationUtils.<User>exactMatch("username", username)
//        );
//        return this.userRepository.findOne(spec).orElse(null);
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "username", username, "user");
        predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.exactMatch(User.class, "person.email", username, "user"), false);
        return AppUtils.userDtoToEntity(this.userQueryService.getUserDetails(predicate));
    }

    @Override
    public User getByEmail(String email) {
//        Specification<User> spec = Specification.where(
//                SpecificationUtils.<User>exactMatch("person.email", email)
//        );
//        return this.userRepository.findOne(spec).orElse(null);
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "person.email", email, "user");
        return AppUtils.userDtoToEntity(this.userQueryService.getUserDetails(predicate));
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
//        Specification<User> spec = Specification.where(
//                SpecificationUtils.<User>exactMatch("person.mobileNumber", phoneNumber)
//                        .or(SpecificationUtils.<User>exactMatch("person.mobileNumber2", phoneNumber))
//        );
//        return this.userRepository.findOne(spec).orElse(null);
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "person.mobileNumber", phoneNumber, "user");
        predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.exactMatch(User.class, "person.mobileNumber2", phoneNumber, "user"), false);
        return AppUtils.userDtoToEntity(this.userQueryService.getUserDetails(predicate));
    }

    @Override
    public boolean existsByUsername(String username) {
//        Specification<User> spec = Specification.where(
//                SpecificationUtils.<User>exactMatch("username", username)
//        );
//        return this.userRepository.findOne(spec).isPresent();
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "username", username, "user");
        UserDto userDto = this.userQueryService.getUserDetails(predicate);
        return userDto != null && !userDto.getId().equals(0L);
    }

    @Override
    public boolean existsByEmail(String email) {
//        Specification<User> spec = Specification.where(
//                SpecificationUtils.<User>exactMatch("person.email", email)
//        );
//        return this.userRepository.findOne(spec).isPresent();
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "person.email", email, "user");
        UserDto userDto = this.userQueryService.getUserDetails(predicate);
        return userDto != null && !userDto.getId().equals(0L);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
//        Specification<User> spec = Specification.where(
//                SpecificationUtils.<User>exactMatch("person.mobileNumber", phoneNumber)
//                        .or(SpecificationUtils.<User>exactMatch("person.mobileNumber2", phoneNumber))
//        );
//        return this.userRepository.findOne(spec).isPresent();
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "person.mobileNumber", phoneNumber, "user");
        predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.exactMatch(User.class, "person.mobileNumber2", phoneNumber, "user"), false);
        UserDto userDto = this.userQueryService.getUserDetails(predicate);
        return userDto != null && !userDto.getId().equals(0L);
    }

    @Override
    public boolean existsByUsername(String username, Long id) {
        if (id == null || id.equals(0L)) {
            return this.existsByUsername(username);
        }
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "username", username, "user");
        predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.exactMatch(User.class, "id", id, "user").not(), true);
        UserDto userDto = this.userQueryService.getUserDetails(predicate);
        return userDto != null && !userDto.getId().equals(0L);
    }

    @Override
    public boolean existsByEmail(String email, Long id) {
        if (id == null || id.equals(0L)) {
            BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "person.email", email, "user");
            UserDto userDto = this.userQueryService.getUserDetails(predicate);
            return userDto != null;
        }
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "person.email", email, "user");
        predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.exactMatch(User.class, "id", id, "user").not(), true);
        UserDto userDto = this.userQueryService.getUserDetails(predicate);
        return userDto != null && !userDto.getId().equals(0L);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber, Long id) {
        if (id == null || id.equals(0L)) {
            return this.existsByPhoneNumber(phoneNumber);
        }
        BooleanExpression predicate = QuerydslUtils.exactMatch(User.class, "person.mobileNumber", phoneNumber, "user");
        predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.exactMatch(User.class, "person.mobileNumber2", phoneNumber, "user"), false);
        predicate = QuerydslUtils.chainPredicate(predicate, QuerydslUtils.exactMatch(User.class, "id", id, "user").not(), true);
        UserDto userDto = this.userQueryService.getUserDetails(predicate);
        return userDto != null && !userDto.getId().equals(0L);
    }

    @Override
    public void enableUser(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEnabled(true);
            this.userRepository.save(user);
        }
    }

    @Override
    public void disableUser(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEnabled(false);
            this.userRepository.save(user);
        }
    }

    @Override
    public void verifyUser(Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setVerified(true);
            this.userRepository.save(user);
        }
    }

    @Override
    public void changePassword(String currentPassword, String newPassword, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String username = this.jwtUtil.extractUsername(token);
        User user = this.getByUsername(username);
        if (!new BCryptPasswordEncoder().matches(currentPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), String.format("The current password is incorrect"));
        }
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword)); // Encrypt password
        this.userRepository.save(user);
    }
}
