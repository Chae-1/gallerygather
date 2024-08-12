package com.kosa.gallerygather.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_REVIEW_REPLY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExhibitionReviewReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reply")
    private String reply;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "update_date")
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

}
