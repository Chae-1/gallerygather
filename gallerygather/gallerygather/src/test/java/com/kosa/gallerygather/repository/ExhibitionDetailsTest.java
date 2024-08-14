package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.dto.ExhibitionDto;
import com.kosa.gallerygather.dto.ExhibitionLikeDto;
import com.kosa.gallerygather.entity.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ExhibitionDetailsTest {
//    private static final Logger log = LoggerFactory.getLogger(ExhibitionDetailsTest.class);

    @Autowired
    private ExhibitionReviewRepository exhibitionReviewRepository;

//    @Autowired
//    private ExhibitionRepository exhibitionRepository;

    @Autowired
    private ExhibitionReviewReplyRepository exhibitionReviewReplyRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ExhibitionReviewLikeRepository exhibitionReviewLikeRepository;
//    private ExhibitionLikeRepository exhibitionLikeRepository;

//    @Test
//    @Transactional
//    public void exhibitionDetailsTest() {
//        Optional<Exhibition> exhibition = exhibitionRepository.findById(Long.valueOf(17));
//        System.out.println(exhibition.get().getTitle());
//    }

//    @Test
//    @Transactional
//    public void getExhibitionList() {
//        Pageable pageable = PageRequest.of(0, 2, Sort.by("regDate").descending() );
//        List<ExhibitionReview> exhibitionList = exhibitionReviewRepository.findByExhibitionId(1L, pageable);
//        exhibitionList.forEach(exhibition -> {
//            log.info("제목: "+exhibition.getTitle());
//        });
//    }

//    @Test
//    @Transactional
//    public void increaseReadCnt() {
//        exhibitionRepository.increaseReadCount(1L);
//    }

//    @Test
//    @Transactional
//    public void insertExhibitionLike() {
//        ExhibitionLikeDto.RequestExhibitionLike dto = new ExhibitionLikeDto.RequestExhibitionLike(1L, 1L);
//        exhibitionLikeRepository.save(ExhibitionLike.setExhibitionLike(dto));
//    }

//    @Test
//    @Transactional
//    public void getExhibitionLike() {
//        exhibitionLikeRepository.findExhibitionLikeByallId(1L, 1L);
//    }

    //존재확인하기
//    @Test
//    @Transactional
//    public void ifExistt() {
//        boolean result = exhibitionLikeRepository.existsByMemberIdAndExhibitionId(1L, 1L);
//        Assertions.assertTrue(result);
//    }

//    @Test
//    @Transactional
//    public void testReply() {
//        Pageable pageable = PageRequest.of(0, 2, Sort.by("regDate").descending() );
//        List<ExhibitionReviewReply> test = exhibitionReviewReplyRepository.findByExhibitionReviewId(1L, pageable);
//        test.forEach(reply -> {
//            log.info("리뷰 " + reply.getReply());
//        });
//    }

//    @Test
//    @Transactional
//    public void testReplis() {
//        Pageable pageable = PageRequest.of(0, 2, Sort.by("regDate").descending() );
//        ExhibitionReview exhibitionReview = exhibitionReviewRepository.findById(3L)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
//        Page<ExhibitionReviewReply> test = exhibitionReviewReplyRepository.findAllRepliesAboutReview(exhibitionReview, pageable);
//        test.forEach(reply -> {
//            log.info("리뷰 " + reply.getReply());
//        });
//    }

//    @Test
//    @Transactional
//    public void updateReply() {
////        ExhibitionReviewReply reply =  exhibitionReviewReplyRepository.findById(5L).orElseThrow(IllegalArgumentException::new);
////        reply.setReply("댓글 수정이 되는지 보자구요");
////        System.out.println(reply.getId() +" " + reply.getReply());
////        Member member = memberRepository.findByEmail("test1@test.com").orElseThrow();
//        ExhibitionReview review = exhibitionReviewRepository.findById(1L).orElseThrow();
//        ExhibitionReviewReply reply = exhibitionReviewReplyRepository.findByIdAndMemberIdAndExhibitionReview(5L, 1L, review)
//                .orElseThrow(IllegalArgumentException::new);
//        System.out.println(reply.getReply());
//        reply.setReply("수정되나 보자!!");
//        System.out.println("수정 후: " +reply.getReply());
//    }

    @Test
    @Transactional
    public void saveReviewLike() {
        ReviewLike reviewLike = ReviewLike.setReviewLike(1L, 1L);
        exhibitionReviewLikeRepository.save(reviewLike);
        ReviewLike reviewLike1 = exhibitionReviewLikeRepository.findById(1L).get();
        System.out.println("member" + reviewLike1.getMember().getId());
    }
}
