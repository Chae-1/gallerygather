package com.kosa.gallerygather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ExhibitionReviewLikeDto {

    @AllArgsConstructor
    @Getter
    public static class RequestReviewLikeDto {
        private Long memberId;
        private Long reviewId;
    }

    public static class RequestLike {
        private boolean isLike;

        public boolean getIsLike() {
            return isLike;
        }
    }

}
