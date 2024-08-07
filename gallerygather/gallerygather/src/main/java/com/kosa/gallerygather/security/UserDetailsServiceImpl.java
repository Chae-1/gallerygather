package com.kosa.gallerygather.security;

import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Entering in loadUserByUsername Method...");
        Optional<Member> optional = memberRepository
                .findByEmail(username);
        Member findMember = optional
                .orElseThrow(() -> new UsernameNotFoundException("notFound Username"));

        log.info("User Authenticated Successfully..!!!");
        return new UserDetailsImpl(findMember);
    }
}
