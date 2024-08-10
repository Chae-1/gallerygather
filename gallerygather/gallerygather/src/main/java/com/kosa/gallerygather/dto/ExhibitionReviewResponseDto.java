package com.kosa.gallerygather.dto;

<<<<<<< HEAD
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
=======
import lombok.Getter;

@Getter
public class ExhibitionReviewResponseDto {
    private Long id;
    private String title;
    private String content;

>>>>>>> 13f3f8230d0f8c8d7e80b3314d3e8bbb11cbaf9e
}
