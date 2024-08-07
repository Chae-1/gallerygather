package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TBL_EXHIBITION")
@Getter
@Setter
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "place")
    private String place;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "charge")
    private Long charge;

    private String author;

    private String siteUrl;

    private String imgUrl;

    private long likeCount;

    private long readCount;

    private long reviewCount;

    private long avgScore;

    private String genre;

    private String duration;
}