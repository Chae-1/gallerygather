package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyPageInfoRepository extends JpaRepository<Member, Long> {

    // 중복확인
    Optional<Member> findByNickName(String nickName);

    // 회원정보업데이트
//    Member findByEmail(String email);
}

