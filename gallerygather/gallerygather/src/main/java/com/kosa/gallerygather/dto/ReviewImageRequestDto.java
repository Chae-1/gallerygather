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

    /*
   작성자: 이혜연
    */
    // ReviewImage를 원본사진 이름, 경로 받는 코드
    public ReviewImageRequestDto(String originalName, String path) {
        this.originalName = originalName;
        this.path = path;
    }


}

