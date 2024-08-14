package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ExhibitionReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean existsByMemberIdAndExhibitionReviewId(@Param("memberId") Long memberId, @Param("reviewId") Long reviewId);
}
