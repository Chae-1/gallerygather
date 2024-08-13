package com.kosa.gallerygather.service;

import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.ExhibitionReviewReply;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewReplyRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewRepository;
import com.kosa.gallerygather.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class ExhibitionReviewServiceTest {
    @Autowired
    ExhibitionReviewRepository exhibitionReviewRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ExhibitionRepository exhibitionRepository;

    @Autowired
    ExhibitionReviewReplyRepository exhibitionReviewReplyRepository;

    @Test
    @Transactional
    void findExhibitionReviewWithAllReplies() {
//        // given
//        Exhibition exhibition = exhibitionRepository.save(Exhibition
//                .builder()
//                .title("review")
//                .build());
//        Member reviewAuthor = memberRepository.save(Member.ofNewMember("name1", null, "null", null, null));
//        Member replyAuthor1 = memberRepository.save(Member.ofNewMember("name2", null, "null", null, null));
//        Member replyAuthor2 = memberRepository.save(Member.ofNewMember("name3", null, "null", null, null));
//
//        ExhibitionReview review = exhibitionReviewRepository.save(ExhibitionReview.ofNewReview("title1",
//                "리뷰 내용입니다.", null, exhibition, reviewAuthor));
//
//        List<ExhibitionReviewReply> exhibitionReviewReplies = exhibitionReviewReplyRepository.saveAllAndFlush(List.of(ExhibitionReviewReply.ofEmpty(replyAuthor1, review),
//                ExhibitionReviewReply.ofEmpty(replyAuthor2, review)));
//        exhibitionReviewRepository.findExhibitionReviewWithAllReplies(review.getId());
//
//        Assertions.assertThat(exhibitionReviewReplies.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback(false)
    void findExhibitionReviewWithAllRepliesTooManyData() {
        ExhibitionReview review = exhibitionReviewRepository.findById(13L)
                .orElse(null);
        List<Member> all = memberRepository.findAll();
        for (int i = 0; i < 10000; i++) {
            for (Member member : all) {
                List<ExhibitionReviewReply> exhibitionReviewReplies = exhibitionReviewReplyRepository.saveAllAndFlush(List.of(
                        ExhibitionReviewReply.ofEmpty(member, review)));
            }
        }
    }
}