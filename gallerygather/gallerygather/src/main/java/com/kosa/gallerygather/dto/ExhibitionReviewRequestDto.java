package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExhibitionReviewRequestDto {

    private String title;
    private String content;
    private Long rating;
    private LocalDate viewDate;
    private Member member;
    private Exhibition exhibition;

    public ExhibitionReview toEntity(Member member, Exhibition exhibition){
        return ExhibitionReview.builder()
                .title(title)
                .content(content)
                .rating(rating)
                .viewDate(viewDate)
                .member(member)
                .exhibition(exhibition)
                .build();

    }

}
