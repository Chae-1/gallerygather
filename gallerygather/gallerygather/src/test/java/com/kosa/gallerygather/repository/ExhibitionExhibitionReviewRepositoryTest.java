package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ExhibitionExhibitionReviewRepositoryTest {
    @Autowired
    ExhibitionReviewRepository exhibitionReviewRepository;

    @Test
    void update() {
        // 1. 댓글을 작성한다.
        // 2.
        List<ExhibitionReview> hyeongil = exhibitionReviewRepository.findByContent("hyeongil");
    }


}