package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.ReviewImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewImageRequestDto {
    private String path;
    private String originalName;

    // 기본 생성자
    public ReviewImageRequestDto() {}

    public ReviewImageRequestDto(String originalName, String path) {
        this.originalName = originalName;
        this.path = path;
    }
}

