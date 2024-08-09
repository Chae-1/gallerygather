package com.kosa.gallerygather.service;

import com.kosa.gallerygather.entity.RefreshToken;
import com.kosa.gallerygather.repository.MemberRepository;
import com.kosa.gallerygather.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public RefreshToken createRefreshToken() {
        return null;
    }

}
