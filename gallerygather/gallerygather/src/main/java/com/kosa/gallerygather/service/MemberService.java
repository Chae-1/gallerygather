package com.kosa.gallerygather.service;

import com.kosa.gallerygather.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kosa.gallerygather.entity.Member;


@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public void doLogin() {

    }
}
