package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.ReviewImage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReviewImageResponseDto {
    private String path;
    private String originalName;

    // 기본 생성자
    public ReviewImageResponseDto() {}

    /*
   작성자: 이혜연
    */
    // ReviewImage를 내보내는 코드
    public ReviewImageResponseDto(ReviewImage reviewImage) {
        this.path = reviewImage.getPath();
        this.originalName = reviewImage.getOriginalName();
    }
}
