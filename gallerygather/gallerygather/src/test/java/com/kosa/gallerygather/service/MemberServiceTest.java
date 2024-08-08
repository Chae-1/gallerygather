package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.MemberDuplicationCheckDto;
import com.kosa.gallerygather.dto.JoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

/**
 * 작성자 : 채형일
 */
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("이미 가입된 회원의 경우 응답이 false로 생성")
    void doesDuplicatedEmail() {
        // given
        JoinRequest joinRequest = new JoinRequest("coguddlf1000@naver.com", "hyeongil", "1234", LocalDate.now(), "c");
        memberService.joinMember(joinRequest);

        // when
        MemberDuplicationCheckDto memberDuplicationCheckDto = memberService.checkDuplicatedEmail(joinRequest.getEmail());

        // then
        assertThat(memberDuplicationCheckDto.isCanJoin())
                .isEqualTo(true);
    }

    @Test
    @DisplayName("가입 되지 않은 회원의 경우 응답이 true로 생성")
    void doesNotDuplicatedEmail() {
        // given
        JoinRequest joinRequest = new JoinRequest("coguddlf1000@naver.com", "hyeongil", "1234", LocalDate.now(), "c");

        // when
        MemberDuplicationCheckDto memberDuplicationCheckDto = memberService.checkDuplicatedEmail(joinRequest.getEmail());

        // then
        assertThat(memberDuplicationCheckDto.isCanJoin())
                .isEqualTo(false);
    }
}