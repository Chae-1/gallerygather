package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.ExhibitionReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExhibitionReviewDto {

    // Request Review list
    @Builder
    @AllArgsConstructor
    @Getter
    public static class RequestReviewList {
        private long id;
        private String title;
        private String content;
        private LocalDateTime regDate;
        private Double rating;
        private String reviewer;

        public RequestReviewList(ExhibitionReview review) {
            this.id = review.getId();
            this.title = review.getTitle();
            this.content = review.getContent();
            this.regDate = review.getRegDate();
            this.rating = review.getRating();
            this.reviewer = review.getMember().getName();

        }
    }


}
