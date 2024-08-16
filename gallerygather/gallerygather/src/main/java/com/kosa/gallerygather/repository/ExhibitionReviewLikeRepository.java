package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
/*
작성자: 오지수
 */
public interface ExhibitionReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean existsByMemberIdAndExhibitionReviewId(@Param("memberId") Long memberId, @Param("reviewId") Long reviewId);

    Optional<ReviewLike> findByMemberIdAndExhibitionReview(@Param("memberId") Long memberId, ExhibitionReview exhibitionReview);

}
