package com.kosa.gallerygather.entity;

import com.kosa.gallerygather.dto.ExhibitionReviewLikeDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TBL_REVIEWLIKE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibit_review_id")
    private ExhibitionReview exhibitionReview;

    public static ReviewLike setReviewLike(Long memberId, Long reviewId) {
        ExhibitionReview exhibitionReview = new ExhibitionReview();
        exhibitionReview.setId(reviewId);
        Member member = new Member();
        member.setId(memberId);
        ReviewLike reviewLike = new ReviewLike();
        reviewLike.member = member;
        reviewLike.exhibitionReview = exhibitionReview;
        return reviewLike;
    }


}
