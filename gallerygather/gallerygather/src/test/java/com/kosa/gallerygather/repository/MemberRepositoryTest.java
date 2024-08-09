package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void saveTest() {
        Member member = Member.ofNewMember("a", "a", "a",null,"a");
        Member savedMember = memberRepository.saveAndFlush(member);

        // 기본키로 조회할 수 있는 기능
        Member selectedMember2 = memberRepository.findByEmail(savedMember.getEmail()).get();

        Assertions.assertThat(selectedMember2.getId()).isEqualTo(savedMember.getId());
    }




}