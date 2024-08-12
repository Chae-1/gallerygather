package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.ReviewImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ReviewImageRequestDto {
    private String path;
    private String originalName;

    // 기본 생성자
    public ReviewImageRequestDto() {}

    // ReviewImage 엔티티를 받아들이는 생성자
    public ReviewImageRequestDto(ReviewImage reviewImage) {
        this.path = reviewImage.getPath();
        this.originalName = reviewImage.getOriginalName();
    }

    public ReviewImageRequestDto(String path) {
        this.path = path;
        this.originalName = path; // 필요에 따라 변경 가능
    }
}

