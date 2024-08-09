package com.kosa.gallerygather.repository;

import org.springframework.data.repository.query.Param;

public interface CustomExhibitionReviewRepository {
    void findExhibitionReviewWithAllReplies(@Param("exhibition") Long exhibition);
}
