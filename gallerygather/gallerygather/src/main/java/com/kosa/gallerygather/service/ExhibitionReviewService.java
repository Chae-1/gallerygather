package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.entity.ReviewImage;
import com.kosa.gallerygather.repository.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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
    private final ExhibitionReviewReplyRepository exhibitionReviewReplyRepository;

    @Transactional
    public ReviewDetailDto write(final ExhibitionReviewRequestDto requestDto, String memberEmail, Long exhibitionId) {

        System.out.println("============== review Details Dto 시작 =================");
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));
        ExhibitionReview exhibitionReview = requestDto.toEntity(member, exhibition);
        ExhibitionReview savedReview = exhibitionReviewRepository.saveAndFlush(exhibitionReview);
        exhibition.updateAvgRating(savedReview.getRating());
        exhibitionRepository.saveAndFlush(exhibition);
        System.out.println("=======================================================" + savedReview);
        // 이미지 저장하고 tbl_review 와 연결
        List<ReviewImage> images = requestDto
                .getImages()
                .stream()
                .map(ReviewImageResponseDto -> {
            ReviewImage reviewImage = new ReviewImage();
            reviewImage.setOriginalName(ReviewImageResponseDto.getOriginalName());
            reviewImage.setPath(ReviewImageResponseDto.getPath());
            reviewImage.setExhibitionReview(savedReview);
            return reviewImageRepository.saveAndFlush(reviewImage);
        }).collect(Collectors.toList());
        // 리뷰에 이미지 리스트 설정
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
        review.increaseViewCount();
        System.out.println("============== review Details Dto 종료 =================");

        return new ReviewDetailDto(review, review.getMember(), exhibition, review.getImages());
    }

    @Transactional
    public boolean deleteReview(String memberEmail, Long reviewId) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID 찾기 오류: " + reviewId));
        if(review.getMember().equals(member)) {
            exhibitionReviewRepository.delete(review);
            exhibitionReviewReplyRepository.deleteByExhibitionReview(review);
            return true;
        }
        return false;
    }

    @Transactional
    public ReviewDetailDto updateReview(ExhibitionReviewRequestDto requestDto,String memberEmail, Long reviewId, Long exhibitionId) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID 찾기 오류: " + reviewId));
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));
        if(!review.getMember().equals(member)) {
            try {
                throw new AccessDeniedException("리뷰 수정 권한 없음");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }
        ExhibitionReview updatedReview = requestDto.toUpdate(review, member, exhibition);

        List<ReviewImage> existingImages = updatedReview.getImages();
        if (existingImages != null) {
            reviewImageRepository.deleteAll(existingImages);
            existingImages.clear();  // 기존 이미지 리스트를 클리어
        }

        List<ReviewImage> updatedImages = requestDto.getImages().stream()
                .map(imageDto -> {
                    ReviewImage reviewImage = new ReviewImage();
                    reviewImage.setOriginalName(imageDto.getOriginalName());
                    reviewImage.setPath(imageDto.getPath());
                    reviewImage.setExhibitionReview(updatedReview);
                    return reviewImageRepository.saveAndFlush(reviewImage);
                })
                .collect(Collectors.toList());

        updatedReview.getImages().addAll(updatedImages);  // 새로운 이미지를 추가


        // 변경된 리뷰와 이미지를 DB에 저장
        exhibitionReviewRepository.saveAndFlush(updatedReview);

        // 평균 평점 업데이트
        exhibition.updateAvgRating(updatedReview.getRating());
        exhibitionRepository.saveAndFlush(exhibition);

        return new ReviewDetailDto(updatedReview, member, exhibition, updatedImages);

//        // 이미지 선 삭제, 후 다시 저장
//        reviewImageRepository.deleteAll(review.getImages());
//        List<ReviewImage> updatedImages = requestDto
//                .getImages()
//                .stream()
//                .map(imageDto -> {
//                    ReviewImage reviewImage = new ReviewImage();
//                    reviewImage.setOriginalName(imageDto.getOriginalName());
//                    reviewImage.setPath(imageDto.getPath());
//                    reviewImage.setExhibitionReview(review);
//                    return reviewImageRepository.saveAndFlush(reviewImage);
//                }).collect(Collectors.toList());
//        review.setImages(updatedImages);
//        exhibition.updateAvgRating(review.getRating());
//        exhibitionReviewRepository.saveAndFlush(updatedReview);
//        return new ReviewDetailDto(review, member, exhibition, updatedImages);
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
