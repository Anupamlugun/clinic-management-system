package com.clinic.cms.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "blacklist:";

    public void blacklist(String token, long expirationMillis) {

        redisTemplate.opsForValue().set(
                PREFIX + token,
                true,
                Duration.ofMillis(expirationMillis)
        );
    }

    public boolean isBlacklisted(String token) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(PREFIX + token)
        );
    }
}