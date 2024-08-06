package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.JwtResponseDto;
import com.kosa.gallerygather.dto.LoginRequest;
import com.kosa.gallerygather.repository.MemberRepository;
import com.kosa.gallerygather.util.JwtService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kosa.gallerygather.entity.Member;


@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public JwtResponseDto doLogin(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authenticate.isAuthenticated()) {
            return new JwtResponseDto(jwtService.generateToken(authenticate.getName()));
        }

        throw new UsernameNotFoundException("User Not Found !!!");
    }
}
