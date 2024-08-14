package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import com.kosa.gallerygather.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExhibitionReviewReplyRepository extends JpaRepository<ExhibitionReviewReply, Long> {

    @Query("select r from ExhibitionReviewReply r where r.exhibitionReview = :exhibitionReview")
    List<ExhibitionReviewReply> findByExhibitReview(@Param("exhibitionReview") ExhibitionReview ExhibitionReview);

    @Query("select r from ExhibitionReviewReply r left join fetch r.member where r.exhibitionReview = :exhibitionReview")
    Page<ExhibitionReviewReply> findByExhibitionReviewWithMember(@Param("exhibitionReview") ExhibitionReview exhibitionReview, Pageable pageable);

    void deleteByExhibitionReview(ExhibitionReview exhibitionReview);
}
