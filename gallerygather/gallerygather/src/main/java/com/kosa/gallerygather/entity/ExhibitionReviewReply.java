package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_REVIEW_REPLY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ExhibitionReviewReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply")
    private String reply;

    @Column(name = "reg_date")
    @CreatedDate
    private LocalDateTime regDate;

    @Column(name = "update_date")
    @LastModifiedDate
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibit_review_id")
    private ExhibitionReview exhibitionReview;


    public ExhibitionReviewReply(String reply, Member member, ExhibitionReview exhibitionReview) {
        this.reply = reply;
        this.member = member;
        this.exhibitionReview = exhibitionReview;
    }

    public static ExhibitionReviewReply ofNewReply(Member member, ExhibitionReview exhibitionReview, String reply) {
        return new ExhibitionReviewReply(reply, member, exhibitionReview);
    }

    public static ExhibitionReviewReply ofEmpty(Member member, ExhibitionReview review) {
        return new ExhibitionReviewReply(null, member, review);
    }

    public Long getReplyReviewId() {
        return exhibitionReview != null ? exhibitionReview.getId() : null;
    }

    public String getReviewTitle() {
        if (exhibitionReview != null && exhibitionReview.getExhibition() != null) {
            return exhibitionReview.getExhibition().getTitle();
        }
        return "No Title";
    }

    //전시번호를 가져오기 위한 코드
    public Long getExhibitionId() {
        if (exhibitionReview != null && exhibitionReview.getExhibition() != null) {
            return exhibitionReview.getExhibition().getId();
        }
        return null;
    }
}

