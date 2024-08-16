package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    /*
   작성자: 이혜연
    */
    // 이미지 경로를 통해 이미지를 검색하는 메서드
    Optional<ReviewImage> findByPath(String path);

    @Modifying
    @Query("delete ReviewImage rm where rm.exhibitionReview = :review")
    int deleteByExhibitionReview(@Param("review") ExhibitionReview review);

}
