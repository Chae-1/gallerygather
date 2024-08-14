package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class ExhibitionReviewRequestDto {

    private String title;
    private String content;
    private Double rating;
    private LocalDate viewDate;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private List<ReviewImageRequestDto> images;

    public ExhibitionReview toEntity(Member member, Exhibition exhibition){
        return ExhibitionReview.builder()
                .title(title)
                .content(content)
                .rating(rating)
                .viewDate(viewDate)
                .regDate(regDate)
                .member(member)
                .exhibition(exhibition)
                .build();
    }

    public ExhibitionReview toUpdate(ExhibitionReview existingReview, Member member, Exhibition exhibition) {
        existingReview.setTitle(this.title);
        existingReview.setContent(this.content);
        existingReview.setRating(this.rating);
        existingReview.setViewDate(this.viewDate);
        existingReview.setUpdateDate(this.updateDate);
        existingReview.setMember(member);
        existingReview.setExhibition(exhibition);
        return existingReview;
    }
}
