package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.CompleteJoinMemberDto;
import com.kosa.gallerygather.dto.JoinRequest;
import com.kosa.gallerygather.dto.JwtResponseDto;
import com.kosa.gallerygather.dto.LoginRequest;
import com.kosa.gallerygather.repository.MemberRepository;
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


@Service
@RequiredArgsConstructor
@Slf4j //
public class MemberService {

    private final MemberRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public JwtResponseDto doLogin(LoginRequest request) {
        log.info("인증 확인 중...");
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        // 필요시 refreshToken 을 발급해서 넘겨준다.
        // -> accessToken의 특성 상,
        if (authenticate.isAuthenticated()) {
            return new JwtResponseDto(jwtService.generateToken(authenticate.getName()), request.getEmail());
        }

        log.info("인증 실패 !!");
        throw new UsernameNotFoundException("User Not Found !!!");
    }

    public CompleteJoinMemberDto joinMember(JoinRequest joinRequest) {
        Member member = Member.ofNewMember(joinRequest.getName(), joinRequest.getEmail(),
                encodedPassword(joinRequest.getPassword()), joinRequest.getDateOfBirth());
        Member saveMember = memberRepository.save(member);

        return CompleteJoinMemberDto.ofNewMemberResponse(saveMember.getEmail(),
                saveMember.getName(), saveMember.getRegDate());
    }

    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
