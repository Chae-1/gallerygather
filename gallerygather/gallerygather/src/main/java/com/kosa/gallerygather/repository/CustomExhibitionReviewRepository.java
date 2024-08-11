package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.dto.ReviewDetailDto2;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomExhibitionReviewRepository {
    List<ReviewDetailDto2> findExhibitionReviewWithAllReplies(@Param("exhibition") Long exhibition);
}
