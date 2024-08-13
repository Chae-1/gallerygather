package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ExhibitionReviewRepository extends JpaRepository<ExhibitionReview, Long> {

    Optional<ExhibitionReview> findByIdAndExhibitionId(Long reviewId,Long exhibitionId);
    List<ExhibitionReview> findByContentContains(String content);

    Page<ExhibitionReview> findByExhibitionId(@Param("exhibitionId") Long exhibitionId, Pageable pageable);
}


