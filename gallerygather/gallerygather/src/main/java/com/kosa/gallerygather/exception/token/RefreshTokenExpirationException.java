package com.kosa.gallerygather.exception.token;
/*
    작성자 : 채형일
 */
public class RefreshTokenExpirationException extends TokenException{

    // ControllerAdvice에서 이를 받아서 처리.

    public RefreshTokenExpirationException() {
        super("토큰이 이미 만료되었습니다.");
    }
}
