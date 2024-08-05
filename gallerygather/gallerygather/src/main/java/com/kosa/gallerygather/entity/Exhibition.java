package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "TBL_EXHIBITION")
@Getter
public class Exhibition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String place;

    private String description;

    private Date start_date;

    private Date end_date;

    private long charge;

    private String author;

    private String site_url;

    private String img_url;

    private long like_count;

    private long read_count;

    private long review_count;

    private long avg_score;

    private String genre;

    private String duration;




}
