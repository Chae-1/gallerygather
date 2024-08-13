package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "members")
@RequestMapping("/api/members")
public class ApiMemberController {

    private final MemberService memberService;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthDto.SuccessfulLoginResultDto> login(@RequestBody LoginRequest loginRequest) {
        log.info("{}", loginRequest);
        AuthDto.SuccessfulLoginResultDto successfulLoginResultDto = memberService.doLogin(loginRequest);
        return ResponseEntity.ok(successfulLoginResultDto);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<AuthDto.LogoutResultDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestBody TokenDto tokenDto) {
        AuthDto.LogoutResultDto logout = memberService.logout(tokenDto, userDetails.getEmail());
        return ResponseEntity.ok(logout);
    }

    @PostMapping("/join")
    public ResponseEntity<CompleteJoinMemberDto> join(@RequestBody JoinRequest request) {
        log.info("{}", request);
        CompleteJoinMemberDto dto = memberService.joinMember(request);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/check")
    public ResponseEntity<MemberDuplicationCheckDto> checkDuplicatedEmail(@RequestBody String email) {
        return ResponseEntity.ok(memberService.checkDuplicatedEmail(email));
    }


    @PostMapping("/auth/refresh")
    public SuccessfulLoginResultDto reissueToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return memberService.reissueToken(refreshTokenDto);
    }

    //유은
    @PostMapping("/change-password")
    public ResponseEntity<String> ChangePassword(@RequestBody MyPageChangePasswordDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();  // JWT에서 추출된 이메일

        System.out.println("JWT에서 추출한 이메일: " + userEmail);
        System.out.println("비밀번호변경요청");

        memberService.changePassword(request, userEmail);

        System.out.println("비밀번호변겅됬나(컨트롤러)" + request.getNewPassword());
        return ResponseEntity.ok("비밀번호 변경 완료");
    }

}

