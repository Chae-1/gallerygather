package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private LocalDateTime regDate;

    private Double rating;

    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "exhibitionReview", cascade = CascadeType.PERSIST)
    private List<ExhibitionReviewReply> reviewReplies = new ArrayList<>();


    private ExhibitionReview(String title, String content, LocalDateTime regDate,
                             Double rating, LocalDateTime updateDate, Exhibition exhibition, Member member) {
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.rating = rating;
        this.updateDate = updateDate;
        this.exhibition = exhibition;
        this.member = member;
    }

    public static ExhibitionReview ofNewReview(String title, String content, Double rating, Exhibition exhibition, Member member) {
        return new ExhibitionReview(title, content, LocalDateTime.now(), rating, LocalDateTime.now(), exhibition, member);
    }

    public void addReplies(ExhibitionReviewReply... replies) {
        for (ExhibitionReviewReply reply : replies) {
            this.reviewReplies.add(reply);
        }
    }

}