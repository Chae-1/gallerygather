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
    private Integer avgRating;

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

}