package com.kosa.gallerygather.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AuthDto {

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SuccessfulLoginResultDto {
        private String accessToken;
        private String refreshToken;
        private String email;
        private String nickName;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LogoutResultDto {
        private String message;
        private boolean successful;

        public static LogoutResultDto ofSuccess() {
            return new LogoutResultDto("성공적으로 로그아웃 처리가 완료되었습니다.", true);
        }

        public static LogoutResultDto ofFailure() {
            return new LogoutResultDto("성공적으로 로그아웃 처리가 완료되었습니다.", true);
        }
    }

}
