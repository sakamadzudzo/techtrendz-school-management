package zw.co.techtrendz.school.management.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.view.UserDto;

import java.util.List;

@Service
public interface UserService {

    public User save(User user);

    public List<User> saveAll(List<User> users);

    public User getById(Long id);

    public Page<User> getAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<User> getAllByExample(User user, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<User> getAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public UserDto getViewById(Long id);

    public Page<UserDto> getViewAll(int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<UserDto> getViewAllByExample(User user, int page, int size, List<String> sortBy, Sort.Direction sortDir);

    public Page<UserDto> getViewAllByFilter(int page, int size, List<String> sortBy, Sort.Direction sortDir, String filter);

    public User getByUsername(String username);

    public User getByEmail(String email);

    public User getByPhoneNumber(String phoneNumber);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    public boolean existsByPhoneNumber(String phoneNumber);

    public void enableUser(Long id);

    public void disableUser(Long id);

    public void verifyUser(Long id);
}
