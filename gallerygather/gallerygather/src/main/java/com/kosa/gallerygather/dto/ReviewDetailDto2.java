package com.kosa.gallerygather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailDto2 {
    private Long reviewAuthorId;
    private Long reviewId;
    private String reviewAuthorNickName;
    private String reviewTitle;
    private String reviewContent;
    private Double reviewRating;
    private int totalReplyCount;

    private List<ExhibitionReviewReplyDto> reviewReplies;
}
