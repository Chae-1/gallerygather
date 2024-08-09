package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReviewDetailDto2 {
    private Long reviewAuthorId;
    private Long reviewId;
    private String reviewTitle;
    private Double reviewRating;
    private int totalReplyCount;
    private String reviewContent;
    private List<ExhibitionReviewReplyDto> reviewReplies;
}
