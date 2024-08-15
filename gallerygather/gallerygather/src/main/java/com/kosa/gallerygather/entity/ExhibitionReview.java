package com.kosa.gallerygather.entity;

import com.kosa.gallerygather.dto.ExhibitionReviewRequestDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Lob
    private String content;

    private Double rating;

    private LocalDate viewDate;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ColumnDefault("0")
    private int viewCount;

    @ColumnDefault("0")
    private int replyCount;

    @ColumnDefault("0")
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @OneToMany(mappedBy = "exhibitionReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "exhibitionReview", cascade = CascadeType.PERSIST, orphanRemoval = true)
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

    public boolean increaseViewCount() {
        viewCount++;
        return true;
    }

    // 이미지 추가
    public void addImage(ReviewImage image) {
        images.add(image);
        image.setExhibitionReview(this);
    }

    // 이미지 제거
    public void removeImage(ReviewImage image) {
        images.remove(image);
        image.setExhibitionReview(null); // 이미지에서 리뷰와의 관계도 끊음
    }

    public static ExhibitionReview ofNewReview(String title, String content, Double rating, LocalDate viewDate, Member member, Exhibition exhibition) {
        return new ExhibitionReview(title, content, rating, viewDate, LocalDateTime.now(), LocalDateTime.now(), member, exhibition);

    }

    public void addReplies(ExhibitionReviewReply... replies) {
        for (ExhibitionReviewReply reply : replies) {
            this.reviewReplies.add(reply);
        }
    }

    public ExhibitionReview(Long reviewId) {
        this.id = reviewId;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseReplyCount() { this.replyCount++; }

    public void decreaseReplyCount() { this.replyCount--; }

    public ExhibitionReview update(ExhibitionReviewRequestDto requestDto, Member member, Exhibition exhibition) {
        setTitle(requestDto.getTitle());
        setContent(requestDto.getContent());
        setRating(requestDto.getRating());
        setViewDate(requestDto.getViewDate());
        setUpdateDate(requestDto.getUpdateDate());
        setMember(member);
        setExhibition(exhibition);
        return this;
    }

    public boolean isWriteFor(Member member) {
        return member == this.member;
    }
}