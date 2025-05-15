package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.repository.UserRepository;
import zw.co.techtrendz.school.management.api.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<User> saveAll(List<User> users) {
        return this.userRepository.saveAll(users);
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<User> getAll(int page, int size, String[] sortBy, Sort.Direction sortDir) {
        return this.userRepository.findAll(PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
    }

    @Override
    public Page<User> getAllByExample(User user, int page, int size, String[] sortBy, Sort.Direction sortDir) {
        if (user == null) {
            return null;
        } else {
            Example<User> example = Example.of(user);
            return this.userRepository.findAll(example, PageRequest.of(page, size, Sort.by(sortDir, sortBy)));
        }
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return this.userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return this.userRepository.existsByPhoneNumber(phoneNumber);
    }
}
