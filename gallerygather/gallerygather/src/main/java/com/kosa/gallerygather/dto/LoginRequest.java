package com.kosa.gallerygather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * 작성자 : 채형일
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
