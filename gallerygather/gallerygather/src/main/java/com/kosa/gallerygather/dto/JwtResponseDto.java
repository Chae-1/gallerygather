package com.kosa.gallerygather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JwtResponseDto {
    private String accessToken;
    private String email;
}
