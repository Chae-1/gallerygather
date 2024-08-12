package com.kosa.gallerygather.dto;

//유은
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor//기본생성자
@AllArgsConstructor//필드파라미터
public class MyPageReviewListResponseDto {
    private long id;
    private String title;
    private String content;
    private Double rating;
    private String exhibitTitle;
    private Long exhibitId;

}
