package com.kosa.gallerygather.service;

import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.entity.RefreshToken;
import com.kosa.gallerygather.exception.token.RefreshTokenExpirationException;
import com.kosa.gallerygather.repository.MemberRepository;
import com.kosa.gallerygather.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    // 새로운 정보를 발급해야할 때 생성
    public RefreshToken createRefreshToken(String email) {

        // email로 조회했을 때 refreshToken이 존재한다면, 해당 RefreshToken을 반환해주면 된다.
        RefreshToken findRefreshToken = refreshTokenRepository.findByUserEmail(email);
        if (findRefreshToken != null) {
            return findRefreshToken;
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .member(memberRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다.")))
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plus(Duration.ofHours(3)))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        // 유효한가?
        log.info("토큰 검증합니다.");
        if (token.isNotValidToken()) {
            log.info("토큰 {}이 만료되었습니다.", token.getToken());
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpirationException();
        }
        Optional<String> d = Optional.of("d");
        String s = d.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전시 정보입니다."));
        return token;
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }
}
