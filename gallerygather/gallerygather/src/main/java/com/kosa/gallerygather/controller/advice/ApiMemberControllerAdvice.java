package com.kosa.gallerygather.controller.advice;

import com.kosa.gallerygather.exception.member.ExistMemberException;
import com.kosa.gallerygather.exception.token.RefreshTokenExpirationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
    작성자 : 채형일
 */
@ControllerAdvice(basePackages = "com.kosa.gallerygather.controller")
public class ApiMemberControllerAdvice {

    @ExceptionHandler(RefreshTokenExpirationException.class)
    public ResponseEntity<String> failToAuthRefresh() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Refresh Not Accepted");
    }

    @ExceptionHandler(ExistMemberException.class)
    public ResponseEntity<String> failToJoin() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("이메일이 중복되었습니다.");
    }

}
