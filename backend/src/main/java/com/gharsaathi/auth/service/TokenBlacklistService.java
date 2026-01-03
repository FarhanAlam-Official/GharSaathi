package com.gharsaathi.auth.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.auth.model.TokenBlacklist;
import com.gharsaathi.auth.repository.RefreshTokenRepository;
import com.gharsaathi.auth.repository.TokenBlacklistRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenBlacklistService {

    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public boolean isTokenBlacklisted(String tokenId) {
        return tokenBlacklistRepository.existsByTokenId(tokenId);
    }

    @Transactional
    public void blacklistToken(String tokenId, LocalDateTime expiresAt, String reason) {
        if (!tokenBlacklistRepository.existsByTokenId(tokenId)) {
            TokenBlacklist blacklist = TokenBlacklist.builder()
                    .tokenId(tokenId)
                    .expiresAt(expiresAt)
                    .reason(reason)
                    .build();
            tokenBlacklistRepository.save(blacklist);
            log.info("Token blacklisted: {} for reason: {}", tokenId, reason);
        }
    }

    @Scheduled(cron = "0 0 2 * * ?") // Run daily at 2 AM
    @Transactional
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenBlacklistRepository.deleteExpiredTokens(now);
        refreshTokenRepository.deleteExpiredTokens(now);
        log.info("Cleaned up expired tokens at {}", now);
    }
}
