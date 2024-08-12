package com.kosa.gallerygather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ExhibitionLikeDto {

    @AllArgsConstructor
    @Getter
    public static class RequestExhibitionLike {
        private long exhibitionId;
        private long memberId;
    }
}
