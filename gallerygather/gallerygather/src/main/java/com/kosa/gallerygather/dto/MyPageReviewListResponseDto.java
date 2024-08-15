package com.kosa.gallerygather.dto;

//유은
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private int reviewCount;
    private LocalDateTime updateDate;

}
