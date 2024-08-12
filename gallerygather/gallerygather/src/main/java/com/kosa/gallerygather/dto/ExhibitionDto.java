package com.kosa.gallerygather.dto;

import com.kosa.gallerygather.entity.Exhibition;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor // 쓰다 필요없는거 삭제해야함
public class ExhibitionDto {

    private long id;

    private String title;
    private String description;
    private String author;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private String charge;
    private String siteUrl;
    private String imgUrl;
    private int likeCount;
    private int readCount;
    private int reviewCount;
    private double avgRating;

    public ExhibitionDto (Exhibition exhibition) {
        this.id = exhibition.getId();
        this.title = exhibition.getTitle();
        this.description = exhibition.getDescription();
        this.author = exhibition.getAuthor();
        this.place = exhibition.getPlace();
        this.startDate = exhibition.getStartDate();
        this.endDate = exhibition.getEndDate();
        this.siteUrl = exhibition.getSiteUrl();
        this.imgUrl = exhibition.getImgUrl();
        this.charge = exhibition.getCharge();
        this.likeCount = exhibition.getLikeCount();
        this.readCount = exhibition.getReadCount();
        this.reviewCount = exhibition.getReviewCount();
        this.avgRating = exhibition.getAvgRating();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ExhibitionCardDto {
        private Long exhibitionId;
        private String imageUrl;
        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
    }

}
