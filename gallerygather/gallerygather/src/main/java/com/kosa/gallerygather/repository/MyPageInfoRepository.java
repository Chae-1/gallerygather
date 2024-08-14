package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyPageInfoRepository extends JpaRepository<Member, Long> {

    // 중복확인
    Optional<Member> findByNickName(String nickName);

    // 이메일로 회원 삭제
    void deleteByEmail(String email);
}

