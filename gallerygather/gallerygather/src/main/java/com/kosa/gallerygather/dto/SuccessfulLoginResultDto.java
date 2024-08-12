package com.kosa.gallerygather.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SuccessfulLoginResultDto {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String nickName;
}
