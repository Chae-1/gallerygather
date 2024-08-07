package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.CompleteJoinMemberDto;
import com.kosa.gallerygather.dto.JoinRequest;
import com.kosa.gallerygather.dto.JwtResponseDto;
import com.kosa.gallerygather.dto.LoginRequest;
import com.kosa.gallerygather.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "members")
@RequestMapping("/api/members")
public class ApiMemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequest loginRequest) {
        log.info("{}", loginRequest);
        JwtResponseDto jwtResponseDto = memberService.doLogin(loginRequest);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/join")
    public ResponseEntity<CompleteJoinMemberDto> join(@RequestBody JoinRequest request){
        log.info("{}", request);
        CompleteJoinMemberDto dto = memberService.joinMember(request);
        return ResponseEntity.ok(dto);
    }
}

