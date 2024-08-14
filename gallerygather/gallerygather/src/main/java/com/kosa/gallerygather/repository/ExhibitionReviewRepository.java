package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ExhibitionReviewRepository extends JpaRepository<ExhibitionReview, Long> {

    Optional<ExhibitionReview> findByIdAndExhibitionId(Long reviewId,Long exhibitionId);
    List<ExhibitionReview> findByContentContains(String content);

    /*
    작성자: 오지수
    exhibition과 id로 유효성검사
     */
    Optional<ExhibitionReview> findByIdAndExhibition(Long id, Exhibition exhibition);

    /*
    작성자: 오지수
    exhibitionId로 리뷰 페이지 리스트 가져오기
     */
    Page<ExhibitionReview> findByExhibitionId(@Param("exhibitionId") Long exhibitionId, Pageable pageable);
}


