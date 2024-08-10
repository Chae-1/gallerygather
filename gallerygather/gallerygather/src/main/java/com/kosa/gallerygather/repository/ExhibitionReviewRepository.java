package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ExhibitionReviewRepository extends JpaRepository<ExhibitionReview, Long> {

    List<ExhibitionReview> findByContentContains(String content);

}