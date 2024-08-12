package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import com.kosa.gallerygather.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReviewDetailDto {
    private Long reviewId;
    private String title;
    private String content;
    private Double rating;
    private LocalDate viewDate;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private String authorName;
    private Long exhibitionId;
    private String exhibitionImgUrl;

   // private List<ExhibitionReviewReply> reviewReplies;

    public ReviewDetailDto(ExhibitionReview review, Member member, Exhibition exhibition) {
        this.reviewId = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.viewDate = review.getViewDate();
        this.regDate = review.getRegDate();
        this.updateDate = review.getUpdateDate();
        this.authorName = member.getNickName();
        this.exhibitionId = exhibition.getId();
        this.exhibitionImgUrl = exhibition.getImgUrl();


    }
}
