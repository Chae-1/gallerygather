package com.kosa.gallerygather.exception.member;

public class ExistMemberException extends MemberException {

    public ExistMemberException() {
        this("이미 존재하는 회원 입니다.");
    }

    public ExistMemberException(String message) {
        super(message);
    }
}
