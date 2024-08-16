package com.kosa.gallerygather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//유은
@Setter
@Getter
@ToString
@NoArgsConstructor
public class MyPageChangePasswordDto {
    private String email;
    private String currentPassword;
    private String newPassword;
}
