package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExhibitionLikeRepository extends JpaRepository<ExhibitionLike, Long> {
    // insert는 save 사용

    @Query("SELECT el FROM ExhibitionLike el WHERE el.member.id = :memberId AND el.exhibition.id = :exhibitionId")
    Optional<ExhibitionLike> findExhibitionLikeByallId(@Param("memberId") Long memberId, @Param("exhibitionId") Long exhibitionId);

    // 존재 여부 확인하기
    boolean existsByMemberIdAndExhibitionId(@Param("memberId") Long memberId, @Param("exhibitionId") Long exhibitionId);

    //유은 - 좋아요한 모든 전시 가져오는 쿼리
    @Query("SELECT e FROM Exhibition e JOIN ExhibitionLike el ON e.id = el.exhibition.id WHERE el.member.id = :memberId")
    List<Exhibition> findLikedExhibitionsByMemberId(@Param("memberId") Long memberId);

    //유은 - 특정 사용자가 좋아요한 전시회의 수를 반환
    int countByMemberId(Long memberId);

}
