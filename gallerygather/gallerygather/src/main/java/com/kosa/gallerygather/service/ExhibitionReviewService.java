package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.entity.ReviewImage;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import com.kosa.gallerygather.repository.MemberRepository;
import com.kosa.gallerygather.repository.ExhibitionReviewRepository;
import com.kosa.gallerygather.repository.ReviewImageRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
// final 필드 생성자 생성
public class ExhibitionReviewService {

    private final ExhibitionReviewRepository exhibitionReviewRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final MemberRepository memberRepository;
    private final ReviewImageRepository reviewImageRepository;

    @Transactional
    public ReviewDetailDto write(final ExhibitionReviewRequestDto requestDto, String memberEmail, Long exhibitionId) {

        System.out.println("============== review Details Dto 시작 =================");
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));
        ExhibitionReview exhibitionReview = requestDto.toEntity(member, exhibition);
        ExhibitionReview savedReview = exhibitionReviewRepository.saveAndFlush(exhibitionReview);
        System.out.println("=======================================================" + savedReview);

        // 3. 이미지 저장하고 tbl_review 와 연결
        List<ReviewImage> images = requestDto
                .getImages()
                .stream()
                .map(ReviewImageRequestDto -> {
            ReviewImage reviewImage = new ReviewImage();
            reviewImage.setPath(ReviewImageRequestDto.getPath());
            reviewImage.setOriginalName(ReviewImageRequestDto.getOriginalName());
            reviewImage.setExhibitionReview(savedReview);
            return reviewImageRepository.saveAndFlush(reviewImage);
        }).collect(Collectors.toList());
        // 4. 리뷰에 이미지 리스트 설정
        savedReview.setImages(images);
        return new ReviewDetailDto(savedReview, member, exhibition, images);
    }

    @Transactional
    public ReviewDetailDto getReviewDetail(Long exhibitionId, Long reviewId) {
        System.out.println("============== review Details Dto 시작 =================");
        // exhibition 1 -> review m -> member m
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID 찾기 오류: " + reviewId));
        System.out.println("============== review Details Dto 종료 =================");

        return new ReviewDetailDto(review, review.getMember(), exhibition, review.getImages());
    }


    // 작성자: 오지수
    // 전시 상세 페이지에서 하단의 리뷰 리스트를 가져오는 Service
    public List<ExhibitionReviewDto.RequestReviewList> getExhibitionReviews(Long exhibitionId, PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto
                .getPageNo()-1, pageRequestDto.getPagePer(), Sort.by("regDate").descending());
        List<ExhibitionReview> exhibitionReviews = exhibitionReviewRepository.findByExhibitionId(exhibitionId, pageable);
        return exhibitionReviews.stream().map(ExhibitionReviewDto.RequestReviewList::new)
                .collect(Collectors.toList());
    }


//    private final ExhibitionReviewReplyRepository exhibitionReviewReplyRepository;

//
//    @Transactional
//    public ReviewDetailDto addReviewToExhibition(String email, Long exhibitionId, ExhibitionReviewRequestDto requestDto) {
//        Member findMember = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new MemberException("가입되지 않은 사용자 입니다."));
//
//        Exhibition findExhibition = exhibitionRepository.findById(exhibitionId)
//                .orElseThrow(() -> new IllegalArgumentException("작성되지 않은 전시글 입니다."));
//
//        ExhibitionReview savedExhibitionReview = exhibitionReviewRepository.saveAndFlush(ExhibitionReview.ofNewReview(requestDto.getTitle(),
//                        requestDto.getContent(),
//                        requestDto.getRating(),
//                        findExhibition, findMember));
//
//        List<ExhibitionReviewReply> exhibitionReviewReplies = exhibitionReviewReplyRepository
//                .findByExhibitReview(savedExhibitionReview);
//
//        return new ReviewDetailDto(findExhibition, exhibitionReviewReplies);
//    }
}
