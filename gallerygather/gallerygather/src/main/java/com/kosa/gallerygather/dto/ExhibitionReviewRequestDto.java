package com.kosa.gallerygather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ExhibitionReviewRequestDto {
    private String title;
    private String content;
    private Double rating;
}
