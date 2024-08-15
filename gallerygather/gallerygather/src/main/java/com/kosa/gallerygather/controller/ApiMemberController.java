package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    //유은 - 비밀번호 변경
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody MyPageChangePasswordDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();  // JWT 에서 추출된 이메일

        System.out.println("JWT 에서 추출한 이메일: " + userEmail);
        System.out.println("비밀번호변경요청");

        memberService.changePassword(request, userEmail);

        System.out.println("비밀번호변겅됬나(컨트롤러)" + request.getNewPassword());
        return ResponseEntity.ok("비밀번호 변경 완료");
    }

    //유은 - 닉네임 중복 확인
    @PostMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");
        System.out.println(nickname);
        boolean isDuplicate = memberService.isNickNameDuplicate(nickname);
        return ResponseEntity.ok(isDuplicate);
    }

    //유은 - 회원 정보 업데이트
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody MyPageDto dto) {
        try {
            // 서비스에서 사용자 정보 업데이트
            Member updatedMember = memberService.updateMember(dto); //영구적인 정보저장을 위해 member
            return ResponseEntity.ok("정보가 성공적으로 저장되었습니다." + updatedMember);
        } catch (RuntimeException e) {
            // 사용자 정보를 찾지 못한 경우 404 상태 코드 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저를 찾을 수 없습니다.");
        }
    }

    //유은 - 회원 기존 정보 가져오기
    @GetMapping("/original")
    public ResponseEntity<MyPageDto> getCurrentUser() {
        try {
            // 서비스에서 사용자 정보 가져오기
            MyPageDto userInfo = memberService.getCurrentMemberInfo();
            return ResponseEntity.ok(userInfo);
        } catch (RuntimeException e) {
            // 사용자 정보를 찾지 못한 경우 404 상태 코드 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}

