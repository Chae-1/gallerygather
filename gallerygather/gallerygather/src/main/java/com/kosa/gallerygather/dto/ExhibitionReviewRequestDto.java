package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
<<<<<<< HEAD
import com.kosa.gallerygather.entity.ReviewImage;
import com.kosa.gallerygather.repository.ReviewImageRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import lombok.*;
>>>>>>> ad1313cf8e17a0e297253e44cd8e5e2e6b5b1b68

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@ToString
public class ExhibitionReviewRequestDto {

    private String title;
    private String content;
    private Double rating;
    private LocalDate viewDate;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private List<ReviewImageRequestDto> images;
    private List<String> imagesToDelete;


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

<<<<<<< HEAD
//    public ExhibitionReview toUpdate(ExhibitionReview existingReview, Member member, Exhibition exhibition) {
//        existingReview.setTitle(this.title);
//        existingReview.setContent(this.content);
//        existingReview.setRating(this.rating);
//        existingReview.setViewDate(this.viewDate);
//        existingReview.setUpdateDate(this.updateDate);
//        existingReview.setMember(member);
//        existingReview.setExhibition(exhibition);
//
//        return existingReview;
//    }

//    public void toUpdate(ExhibitionReview review) {
//        review.setTitle(this.title);
//        review.setContent(this.content);
//        review.setRating(this.rating);
//        review.setViewDate(this.viewDate);
//        review.setUpdateDate(this.updateDate);
//    }
=======
>>>>>>> ad1313cf8e17a0e297253e44cd8e5e2e6b5b1b68
}
