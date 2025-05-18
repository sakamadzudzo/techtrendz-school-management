package zw.co.techtrendz.school.management.api.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import zw.co.techtrendz.school.management.api.entity.User;
import zw.co.techtrendz.school.management.api.exception.UserCreationException;
import zw.co.techtrendz.school.management.api.service.UserService;

public class AppUtils {

    public static String getClientFingerprint(HttpServletRequest request) {
        String ip = AppUtils.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String language = request.getHeader("Accept-Language");

        return DigestUtils.sha256Hex(ip + userAgent + language);
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void validateUser(User user, UserService userService) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new UserCreationException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserCreationException("Password cannot be null or empty");
        }
        if (user.getPerson() == null) {
            throw new UserCreationException("KYC details missing");
        }
        if (user.getPerson().getEmail() == null || user.getPerson().getEmail().isEmpty()) {
            throw new UserCreationException("Email cannot be null or empty");
        }
        if (userService.existsByEmail(user.getPerson().getEmail())) {
            throw new UserCreationException("Email already in use");
        }
        if (userService.existsByUsername(user.getUsername())) {
            throw new UserCreationException("Username already in use");
        }
    }
}
