package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberDuplicationCheckDto {
    private boolean canJoin;
    private String comment;

    private MemberDuplicationCheckDto(boolean canJoin, String comment) {
        this.canJoin = canJoin;
        this.comment = comment;
    }

    public static MemberDuplicationCheckDto generateResultFor(Member member) {
        if (member == null) {
            return notRegistered();
        }
        return registered();
    }

    public static MemberDuplicationCheckDto registered() {
        return new MemberDuplicationCheckDto(false, "이미 가입된 회원.");
    }

    public static MemberDuplicationCheckDto notRegistered() {
        return new MemberDuplicationCheckDto(true, "가입 가능.");
    }
}
