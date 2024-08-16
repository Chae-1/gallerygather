package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.ExhibitionReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExhibitionReviewDto {

    // Request Review list
    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RequestReviewList {
        private long id;
        private String title;
        private String content;
        private LocalDate regDate;
        private Double rating;
        private String reviewer;
        private String nickName;
        private int viewCount;
        private int likeCount;
        private int replyCount;

        public RequestReviewList(ExhibitionReview review) {
            this.id = review.getId();
            this.title = review.getTitle();
            this.content = review.getContent();
            this.regDate = review.getRegDate().toLocalDate();
            this.rating = review.getRating();
            this.viewCount = review.getViewCount();
            this.likeCount = review.getLikeCount();
            this.replyCount = review.getReplyCount();
            this.reviewer = review.getMember().getName();
            this.nickName = review.getMember().getNickName();

        }
    }


}
