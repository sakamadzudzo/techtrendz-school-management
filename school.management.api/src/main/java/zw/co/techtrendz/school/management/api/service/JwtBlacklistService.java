package zw.co.techtrendz.school.management.api.service;

import org.springframework.stereotype.Service;

@Service
public interface JwtBlacklistService {

    void blacklistToken(String token);

    boolean isTokenBlacklisted(String token);
}
