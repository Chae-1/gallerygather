package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.entity.RefreshToken;
import com.kosa.gallerygather.exception.member.ExistMemberException;
import com.kosa.gallerygather.exception.member.MemberException;
import com.kosa.gallerygather.exception.token.RefreshTokenExpirationException;
import com.kosa.gallerygather.repository.MemberRepository;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.util.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.kosa.gallerygather.entity.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/*
    작성자: 채형일
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenService refreshTokenService;
    private final BlockListManager blockListManager;

    public AuthDto.SuccessfulLoginResultDto doLogin(LoginRequest request) {
        log.info("인증 확인 중...");
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // 인증된 회원이라면, refreshToken과 accessToken을 발급한다.
        if (authenticate.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
            String email = userDetails.getEmail();
            String nickName = userDetails.getNickName();
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);

            return AuthDto.SuccessfulLoginResultDto
                    .builder()
                    .email(email)
                    .accessToken(jwtService.generateToken(email))
                    .refreshToken(refreshToken.getToken())
                    .nickName(nickName)
                    .build();
        }

        log.info("인증 실패 !!");
        throw new UsernameNotFoundException("User Not Found !!!");
    }

    public CompleteJoinMemberDto joinMember(JoinRequest joinRequest) {
        // 이미 존재하는 회원의 경우 예외가 발생한다.
        // 정책에 따라서
        validateMember(joinRequest);

        Member member = Member.ofNewMember(joinRequest.getName(), joinRequest.getEmail(),
                passwordEncoder.encode(joinRequest.getPassword()), joinRequest.getDateOfBirth(),
                joinRequest.getNickName());
        Member saveMember = memberRepository.save(member);

        return CompleteJoinMemberDto.ofNewMemberResponse(saveMember.getEmail(),
                saveMember.getName(), saveMember.getRegDate(), saveMember.getNickName());
    }

    private void validateMember(JoinRequest joinRequest) {
        if (memberRepository.findByEmail(joinRequest.getEmail())
                .isPresent()) {
            throw new ExistMemberException();
        }
    }

    public MemberDuplicationCheckDto checkDuplicatedEmail(String email) {
        return MemberDuplicationCheckDto.generateResultFor(
                memberRepository.findByEmail(email)
                        .orElse(null)
        );
    }

    public SuccessfulLoginResultDto reissueToken(RefreshTokenDto refreshTokenDto) {
        log.info("{}", refreshTokenDto.getRefreshToken());
        return refreshTokenService.findByToken(refreshTokenDto.getRefreshToken().trim())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getMember)
                .map(member -> SuccessfulLoginResultDto
                        .builder()
                        .email(member.getEmail())
                        .nickName(member.getNickName())
                        .accessToken(jwtService.generateToken(member.getEmail()))
                        .refreshToken(refreshTokenDto.getRefreshToken()).build())
                .orElseThrow(() -> new RefreshTokenExpirationException());
    }

    //유은 - 비밀번호 변경
    public void changePassword(MyPageChangePasswordDto request, String userEmail) {
        //이메일확인
        System.out.println("로그인된 이메일 :  "+userEmail);
        System.out.println("getCurrentPassword"+request.getCurrentPassword()+"getNewPassword"+request.getNewPassword());

        Member member = memberRepository.findByEmail(userEmail).orElseThrow(()->
        new IllegalArgumentException("회원이 존재하지 않습니다."));


        //현재 비밀번호 확인
        if (!passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
            throw  new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        //새 비밀번호 설정
        System.out.println("get된 비밀번호(서비스) "+member.getPassword());
        member.setPassword(passwordEncoder.encode(request.getNewPassword()));
        System.out.println("set후 get된 비밀번호(서비스) "+member.getPassword());


        // 변경된 회원 정보를 데이터베이스에 저장
        memberRepository.save(member);

    }//MyPageChangePasswordDto


    @Transactional
    public AuthDto.LogoutResultDto logout(TokenDto tokenDto, String email) {
        // 1. 명시적인 로그아웃을 시도했을 때 jwt 토큰을 블랙리스트에 추가한다.
        // 인증과정에서 엑세스 토큰이 블랙리스트에 존재한다면, 차단한다.
        blockListManager.addBlockList(tokenDto.getAccessToken());
        // 2. 로그아웃 대상 유저의 refreshToken을 제거한다.
        // member의 Id와 token을 대상으로 삭제한다.
        if (refreshTokenService.deleteToken(tokenDto.getRefreshToken())) {
            return AuthDto.LogoutResultDto.ofSuccess();
        }
        return AuthDto.LogoutResultDto.ofFailure();
    }
}
