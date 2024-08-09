package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_REVIEW_REPLY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExhibitionReviewReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply")
    private String reply;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibit_review_id")
    private ExhibitionReview exhibitionReview;

    public static ExhibitionReviewReply ofEmpty(Member member, ExhibitionReview exhibitionReview) {
        return new ExhibitionReviewReply(null, "", LocalDateTime.now(),
                LocalDateTime.now(), member, exhibitionReview);
    }
}
