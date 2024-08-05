package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.UserDto;
import com.kosa.gallerygather.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "users")
public class ApiUserController {
    private final MemberService memberService;

    @GetMapping("/api/users")
    public ResponseEntity<UserDto> login() {
        memberService.execution();
        return ResponseEntity.ok(new UserDto("Hi", "email@email"));
    }
}
