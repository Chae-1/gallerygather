package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ExhibitionReviewRepository extends JpaRepository<ExhibitionReview, Long> {

    List<ExhibitionReview> findByContentContains(String content);

    List<ExhibitionReview> findByExhibitionId(@Param("exhibitionId") Long exhibitionId, Pageable pageable);

//    @Query("select er from ExhibitionReview er " +
//            " left join er.exhibition ex" +
//            " left join r.member m " +
//            " where er.member ")
//    Object[] findExhibitionWithReplyWithAssociatedMember(@Param("reviewId") Long reviewId);

}


