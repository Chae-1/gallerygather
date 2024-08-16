package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TBL_EXHIBITION")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "place")
    private String place;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "charge")
    private String charge;

    @Column(name = "author")
    private String author;

    @Column(name ="site_url")
    private String siteUrl;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "like_count")
    @ColumnDefault("0")
    private Integer likeCount;

    @Column(name = "read_count")
    @ColumnDefault("0")
    private Integer readCount;

    @Column(name = "review_count")
    @ColumnDefault("0")
    private Integer reviewCount;

    @Column(name = "avg_rating")
    @ColumnDefault("0")
    private Double avgRating;

    public boolean increaseReadCount(){
        readCount++;
        return true;
    }

    public void increaseLikeCount(){
        likeCount++;
    }

    public void decreaseLikeCount(){
        likeCount--;
    }

    public void updateAvgRating(Double newRating){
        this.reviewCount++;
        this.avgRating = ((this.avgRating * (this.reviewCount - 1) + newRating) / this.reviewCount);
    }

    public Double getAvgRating() {
        if (this.avgRating != null) {
            return Math.round(this.avgRating * 100.0) / 100.0; // 소수점 둘째 자리까지 반올림하여 반환
        }
        return null;
    }

}