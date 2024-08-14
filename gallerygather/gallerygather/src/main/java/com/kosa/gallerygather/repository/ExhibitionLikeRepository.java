package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExhibitionLikeRepository extends JpaRepository<ExhibitionLike, Long> {
    // insert는 save 사용

    @Query("SELECT el FROM ExhibitionLike el WHERE el.member.id = :memberId AND el.exhibition.id = :exhibitionId")
    Optional<ExhibitionLike> findExhibitionLikeByallId(@Param("memberId") Long memberId, @Param("exhibitionId") Long exhibitionId);

    // 존재 여부 확인하기

    boolean existsByMemberIdAndExhibitionId(@Param("memberId") Long memberId, @Param("exhibitionId") Long exhibitionId);
}
