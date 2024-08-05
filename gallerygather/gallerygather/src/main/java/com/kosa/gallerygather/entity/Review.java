package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL")
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    private LocalDateTime date;

    private LocalDateTime update_date;

    private long score;


}