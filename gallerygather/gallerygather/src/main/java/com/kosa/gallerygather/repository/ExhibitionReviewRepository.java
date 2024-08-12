package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ExhibitionReviewRepository extends JpaRepository<ExhibitionReview, Long> {

    //List<ExhibitionReview> findByContentContains(String content);
    Optional<ExhibitionReview> findByIdAndExhibitionId(Long reviewId,Long exhibitionId);
    List<ExhibitionReview> findByContentContains(String content);

    List<ExhibitionReview> findByExhibitionId(@Param("exhibition") Long exhibitionId, Pageable pageable);

}


