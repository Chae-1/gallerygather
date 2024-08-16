package com.kosa.gallerygather.exception.member;
/*
    작성자 : 채형일
 */
public class ExistMemberException extends MemberException {

    public ExistMemberException() {
        this("이미 존재하는 회원 입니다.");
    }

    public ExistMemberException(String message) {
        super(message);
    }
}
