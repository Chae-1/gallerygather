package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.JwtResponseDto;
import com.kosa.gallerygather.dto.LoginRequest;
import com.kosa.gallerygather.dto.UserDto;
import com.kosa.gallerygather.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "users")
@RequestMapping("/api/users")
public class ApiUserController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(LoginRequest loginRequest) {
        JwtResponseDto jwtResponseDto = memberService.doLogin(loginRequest);
        return ResponseEntity.ok(jwtResponseDto);
    }
}
