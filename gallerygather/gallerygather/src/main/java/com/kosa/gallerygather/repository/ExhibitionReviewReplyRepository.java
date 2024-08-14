package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExhibitionReviewReplyRepository extends JpaRepository<ExhibitionReviewReply, Long> {

    @Query("select r from ExhibitionReviewReply r where r.exhibitionReview = :exhibitionReview")
    List<ExhibitionReviewReply> findByExhibitReview(@Param("exhibitionReview") ExhibitionReview ExhibitionReview);

    @Query("select r from ExhibitionReviewReply r left join fetch r.member where r.exhibitionReview = :exhibitionReview")
    Page<ExhibitionReviewReply> findByExhibitionReviewWithMember(@Param("exhibitionReview") ExhibitionReview exhibitionReview, Pageable pageable);

    /*
    작성자: 오지수
     */
    void deleteByExhibitionReview(ExhibitionReview exhibitionReview);

    /*
    리뷰 페이지에서 페이지네이션으로 댓글 리스트 가져오기
     */
    @Query("select rp from ExhibitionReviewReply rp left join fetch rp.member where rp.exhibitionReview = :exhibitionReview" )
    Page<ExhibitionReviewReply> findAllRepliesAboutReview(@Param("exhibitionReview") ExhibitionReview exhibitionReview,
                                                          Pageable pageable);

    /*
    작성자: 오지수
    reviewId, memberId, replyId가 일치하는 reply 가져오기 -> 유효성 겸사 겸 댓글 가져오기
     */
    Optional<ExhibitionReviewReply> findByIdAndMemberIdAndExhibitionReview(Long id, Long memberId, ExhibitionReview exhibitionReview);

}
