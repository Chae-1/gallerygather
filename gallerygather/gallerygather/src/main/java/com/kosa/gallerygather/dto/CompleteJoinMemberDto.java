package com.kosa.gallerygather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 작성자 : 채형일
 * 회원 가입 완료 시 응답으로 보내주는 dto
 */
@Getter
@Setter
@NoArgsConstructor
public class CompleteJoinMemberDto {
    private LocalDateTime regDate;
    private String email;
    private String name;
    private String nickName;

    private CompleteJoinMemberDto(String email, String name, LocalDateTime regDate, String nickName) {
        this.regDate = regDate;
        this.name = name;
        this.email = email;
        this.nickName = nickName;
    }

    public static CompleteJoinMemberDto ofNewMemberResponse(String email, String name,
                                                            LocalDateTime regDate, String nickName) {
        return new CompleteJoinMemberDto(email, name, regDate, nickName);
    }
}
