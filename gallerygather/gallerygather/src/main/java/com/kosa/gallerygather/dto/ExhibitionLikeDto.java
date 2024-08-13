package com.kosa.gallerygather.dto;

import lombok.*;

public class ExhibitionLikeDto {

    @AllArgsConstructor
    @Getter
    public static class RequestExhibitionLike {
        private long exhibitionId;
        private long memberId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    public static class RequestLike {
        private boolean isLike;

        public boolean getIsLike(){
            return isLike;
        }
    }
}
