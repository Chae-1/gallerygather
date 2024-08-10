package com.kosa.gallerygather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 작성자 : 박유은
 */
@Getter
@NoArgsConstructor//기본생성자
@AllArgsConstructor//필드파라미터
public class ExhibitionReviewResponseDto {
    private String title;
    private String content;
    private Double rating;
}
