package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "members")
@RequestMapping("/api/members")
public class ApiMemberController {
    private final MemberService memberService;
    // 작성자 : 채형일
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

    @PostMapping("/emails/check")
    public ResponseEntity<MemberDuplicationCheckDto> checkDuplicatedEmail(@RequestBody String email) {
        return ResponseEntity.ok(memberService.checkDuplicatedEmail(email));
    }
}

