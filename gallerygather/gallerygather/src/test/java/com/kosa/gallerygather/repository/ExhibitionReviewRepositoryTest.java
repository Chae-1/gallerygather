package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import jdk.jfr.TransitionTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ExhibitionReviewRepositoryTest {

    @Autowired
    ExhibitionReviewRepository exhibitionReviewRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void update() {
        // 1. 댓글을 작성한다.

        Member member = memberRepository.findById(1L)
                .get();

        exhibitionReviewRepository.findWithAllImagesById(1L, member)
                .ifPresent(review -> {
                    System.out.println(review.getMember() == member);
                });

    }


}