package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ExhibitionReviewRepositoryTest {
    @Autowired
    ExhibitionReviewRepository exhibitionReviewRepository;

    @Test
    void update() {
        List<ExhibitionReview> hyeongil = exhibitionReviewRepository.findByContent("hyeongil");
    }


}