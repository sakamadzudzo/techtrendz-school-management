package zw.co.techtrendz.school.management.api.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.dto.AuthDto;
import zw.co.techtrendz.school.management.api.entity.User;

import java.util.Map;

@Service
public interface AuthService {

    public ResponseEntity<Map<String, Object>> login(AuthDto authDto, HttpServletRequest request);

    public ResponseEntity<String> logout(HttpServletRequest request);

    public ResponseEntity<String> refresh(HttpServletRequest request);

    public ResponseEntity<String> register(User user, HttpServletRequest request);
}
