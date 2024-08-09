package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_EXHIBIT_REVIEW")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExhibitionReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long rating;

    private LocalDate viewDate;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    private ExhibitionReview(String title, String content, Long rating, LocalDate viewDate, Member member, Exhibition exhibition) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.viewDate = viewDate;
        this.member = member;
        this.exhibition = exhibition;
    }

    public ExhibitionReview(String title, String content, Long rating, LocalDate viewDate, LocalDateTime regDate) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.viewDate = viewDate;
        this.regDate = LocalDateTime.now();
    }


}