package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private Double rating;

    private LocalDate viewDate;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @OneToMany(mappedBy = "exhibitionReview", cascade = CascadeType.PERSIST)
    private List<ExhibitionReviewReply> reviewReplies = new ArrayList<>();

    @Builder
    private ExhibitionReview(String title, String content, Double rating, LocalDate viewDate,
                             LocalDateTime regDate, LocalDateTime updateDate, Member member, Exhibition exhibition) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.viewDate = viewDate;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.member = member;
        this.exhibition = exhibition;
    }

    public static ExhibitionReview ofNewReview(String title, String content, Double rating, LocalDate viewDate, Member member, Exhibition exhibition) {
        return new ExhibitionReview(title, content, rating, viewDate, LocalDateTime.now(), LocalDateTime.now(), member, exhibition);

    }

    public void addReplies(ExhibitionReviewReply... replies) {
        for (ExhibitionReviewReply reply : replies) {
            this.reviewReplies.add(reply);
        }
    }
}