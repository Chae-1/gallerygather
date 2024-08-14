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

    // ReviewImage 엔티티를 받아들이는 생성자
    public ReviewImageResponseDto(ReviewImage reviewImage) {
        this.path = reviewImage.getPath();
        this.originalName = reviewImage.getOriginalName();
    }
}
