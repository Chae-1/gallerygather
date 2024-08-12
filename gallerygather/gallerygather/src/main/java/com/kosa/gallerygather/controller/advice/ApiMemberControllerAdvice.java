package com.kosa.gallerygather.controller.advice;

import com.kosa.gallerygather.exception.token.RefreshTokenExpirationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.kosa.gallerygather.controller")
public class ApiMemberControllerAdvice {

    @ExceptionHandler(RefreshTokenExpirationException.class)
    public ResponseEntity<String> failToAuthRefresh() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Refresh Not Accepted");
    }
}
