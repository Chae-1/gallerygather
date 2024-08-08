package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_EXHIBIT_REVIEW")
@Getter
@Setter
public class ExhibitionReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private LocalDateTime regDate;

    private Long rating;

    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;
}