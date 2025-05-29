package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.exception.UserCreationException;
import zw.co.techtrendz.school.management.api.repository.UserRepository;
import zw.co.techtrendz.school.management.api.service.PersonService;
import zw.co.techtrendz.school.management.api.service.UserService;
import zw.co.techtrendz.school.management.api.utility.AppUtils;
import zw.co.techtrendz.school.management.api.utility.SpecificationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonService personService;

    @Override
    public User save(User user) {
        if(user.getPerson() != null && user.getPerson().getId() != null && !user.getPerson().getId().equals(0L)) {
            user.setPerson(this.personService.getById(user.getPerson().getId()));
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
        return users.stream().map(this::save).toList();
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir) {
        if (sortBy.isEmpty()) {
            sortBy.add("name");
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
                sortBy.add("name");
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
                sortBy.add("name");
            }
            String[] sortFields = sortBy.toArray(new String[sortBy.size()]);

            return this.userRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortDir, sortFields)));
        }
    }

    @Override
    public User getByUsername(String username) {
        Specification<User> spec = Specification.where(
                SpecificationUtils.<User>exactMatch("username", username)
        );
        return this.userRepository.findOne(spec).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        Specification<User> spec = Specification.where(
                SpecificationUtils.<User>exactMatch("person.email", email)
        );
        return this.userRepository.findOne(spec).orElse(null);
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        Specification<User> spec = Specification.where(
                SpecificationUtils.<User>exactMatch("person.mobileNumber", phoneNumber)
                        .or(SpecificationUtils.<User>exactMatch("person.mobileNumber2", phoneNumber))
        );
        return this.userRepository.findOne(spec).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        Specification<User> spec = Specification.where(
                SpecificationUtils.<User>exactMatch("username", username)
        );
        return this.userRepository.findOne(spec).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        Specification<User> spec = Specification.where(
                SpecificationUtils.<User>exactMatch("person.email", email)
        );
        return this.userRepository.findOne(spec).isPresent();
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        Specification<User> spec = Specification.where(
                SpecificationUtils.<User>exactMatch("person.mobileNumber", phoneNumber)
                        .or(SpecificationUtils.<User>exactMatch("person.mobileNumber2", phoneNumber))
        );
        return this.userRepository.findOne(spec).isPresent();
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
}
