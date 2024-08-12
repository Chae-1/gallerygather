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

    @PostMapping("/auth/login")
    public ResponseEntity<SuccessfulLoginResultDto> login(@RequestBody LoginRequest loginRequest) {
        log.info("{}", loginRequest);
        SuccessfulLoginResultDto successfulLoginResultDto = memberService.doLogin(loginRequest);
        return ResponseEntity.ok(successfulLoginResultDto);
    }

    @PostMapping("/join")
    public ResponseEntity<CompleteJoinMemberDto> join(@RequestBody JoinRequest request){
        log.info("{}", request);
        CompleteJoinMemberDto dto = memberService.joinMember(request);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/check")
    public ResponseEntity<MemberDuplicationCheckDto> checkDuplicatedEmail(@RequestBody String email) {
        return ResponseEntity.ok(memberService.checkDuplicatedEmail(email));
    }

    // refreshToken이 만료되면 해당 API를 호출한 이후 본 요청을 다시 요청해야한다.
    // 요청처리하는 부분을 JS에서 메서드로 만들어야 한다.

    @PostMapping("/auth/refresh")
    public SuccessfulLoginResultDto reissueToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return memberService.reissueToken(refreshTokenDto);
    }
}

