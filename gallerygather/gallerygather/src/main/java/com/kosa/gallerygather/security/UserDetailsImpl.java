package com.kosa.gallerygather.security;

import com.kosa.gallerygather.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private String email;
    private String password;
    private String nickName;
    private String auth;

    public UserDetailsImpl(Member findMember) {
        this.email = findMember.getEmail();
        this.auth = findMember.getAuth();
        this.password = findMember.getPassword();
        this.nickName = findMember.getNickName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(auth));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }
}
