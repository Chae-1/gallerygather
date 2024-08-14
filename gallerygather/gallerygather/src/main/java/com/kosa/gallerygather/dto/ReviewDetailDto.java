package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private int viewCount;
    private int likeCount;
    private int replyCount;
    private String authorName;
    private Long exhibitionId;
    private String exhibitionImgUrl;
    private List<ReviewImageResponseDto> images;

    public ReviewDetailDto(ExhibitionReview review, Member member, Exhibition exhibition, List<ReviewImage> images) {
        this.reviewId = review.getId();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.viewDate = review.getViewDate();
        this.regDate = review.getRegDate();
        this.updateDate = review.getUpdateDate();
        this.viewCount = review.getViewCount();
        this.likeCount = review.getLikeCount();
        this.replyCount = review.getReplyCount();
        this.authorName = member.getNickName();
        this.exhibitionImgUrl = exhibition.getImgUrl();
        this.images = images.stream()
                .map(ReviewImageResponseDto::new)
                .collect(Collectors.toList());

    }

    /*
    작성자: 오지수
     */
    @Getter
    public static class ResponseReviewDetailDto {
        private Long reviewId;
        private String title;
        private String content;
        private Double rating;
        private LocalDate viewDate;
        private LocalDate regDate;
        private int viewCount;
        private int likeCount;
        private int replyCount;

        private String authorName;
        private String authorEmail;

        private Long exhibitionId;
        private String exhibitionImgUrl;
        private String exhibitionTitle;

        List<ReviewImageRequestDto> images;

        public ResponseReviewDetailDto(ExhibitionReview review) {
            this.reviewId = review.getId();
            this.title = review.getTitle();
            this.content = review.getContent();
            this.rating = review.getRating();
            this.viewDate = review.getViewDate();
            this.regDate = review.getRegDate().toLocalDate();
            this.viewCount = review.getViewCount();
            this.likeCount = review.getLikeCount();
            this.replyCount = review.getReplyCount();

            this.authorName = review.getMember().getNickName();
            this.authorEmail = review.getMember().getEmail();

            this.exhibitionId = review.getExhibition().getId();
            this.exhibitionImgUrl = review.getExhibition().getImgUrl();
            this.exhibitionTitle = review.getExhibition().getTitle();

            this.images = review.getImages().stream().map(ReviewImageRequestDto::new).collect(Collectors.toList());
        }
    }
}
