package com.kosa.gallerygather.service;

import com.kosa.gallerygather.entity.RefreshToken;
import com.kosa.gallerygather.exception.token.RefreshTokenExpirationException;
import com.kosa.gallerygather.repository.MemberRepository;
import com.kosa.gallerygather.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    // 새로운 정보를 발급해야할 때 생성
    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .member(memberRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다.")))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(6000 * 100))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        // 유효한가?
        if (token.isNotValidToken()) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpirationException();
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }
}
