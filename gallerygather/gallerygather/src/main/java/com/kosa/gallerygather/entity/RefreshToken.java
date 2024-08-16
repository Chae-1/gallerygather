package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
/*
    작성자 : 채형일
    accesstoken 토큰 만료로 인한 refreshToken 발급 엔티티

 */
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime expiryDate;

    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 현재 시간 보다 expiryDate가 이전이다.
    // -> 이미 토큰이 만료되었다.
    public boolean isNotValidToken() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
