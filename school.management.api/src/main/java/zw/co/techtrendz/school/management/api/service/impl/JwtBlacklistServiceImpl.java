package zw.co.techtrendz.school.management.api.service.impl;

import org.springframework.stereotype.Service;
import zw.co.techtrendz.school.management.api.service.JwtBlacklistService;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    private final Set<String> blacklistedTokens = new HashSet<>();

    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}