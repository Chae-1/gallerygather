package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.entity.Member;
import com.kosa.gallerygather.entity.ReviewImage;
import com.kosa.gallerygather.repository.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final ExhibitionReviewLikeRepository exhibitionReviewLikeRepository;

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

    /*
    작성자: 오지수
    viewCount++ 하고 리뷰 정보 가져오기
    로그인 여부, 좋아요 여부 전달하기
     */
    @Transactional
    public Map<String, Object> getReviewDetail(Long exhibitionId, Long reviewId, Long memberId) {
        Map<String, Object> reviewInfo = new HashMap<>();

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));

        ReviewDetailDto.ResponseReviewDetailDto reviewDto = exhibitionReviewRepository.findByIdAndExhibition(reviewId, exhibition)
                .filter(ExhibitionReview::increaseViewCount)
                .map(ReviewDetailDto.ResponseReviewDetailDto::new)
                .orElseThrow(IllegalArgumentException::new);
        reviewInfo.put("reviewDetail", reviewDto);


        if (memberId == null) {
            reviewInfo.put("isLoggedIn", false);
            reviewInfo.put("isLike", false);
            return reviewInfo;
        }
        reviewInfo.put("isLoggedIn", true);
        reviewInfo.put("isLike", exhibitionReviewLikeRepository.existsByMemberIdAndExhibitionReviewId(memberId, reviewId));
        return reviewInfo;
    }

    @Transactional
    public boolean deleteReview(String memberEmail, Long reviewId) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID 찾기 오류: " + reviewId));
        if(review.getMember().equals(member)) {
            exhibitionReviewRepository.delete(review);
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
        if (!review.getMember().equals(member)) {
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
    }

    // 작성자: 오지수
    // 전시 상세 페이지에서 하단의 리뷰 리스트를 가져오는 Service
    public Page<ExhibitionReviewDto.RequestReviewList> getExhibitionReviews(Long exhibitionId, PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto
                .getPageNo()-1, pageRequestDto.getPagePer(), Sort.by("regDate").descending());
        Page<ExhibitionReview> exhibitionReviews = exhibitionReviewRepository.findByExhibitionId(exhibitionId, pageable);
        return exhibitionReviews.map(this::changeDtoRemoveImgTags);
    }

    /*
    작성자: 오지수
    ExhibitionReview를 받아서 Content에서 img 태그를 없애고 ExhibitionReviewDto로 반환
     */
    private ExhibitionReviewDto.RequestReviewList changeDtoRemoveImgTags(ExhibitionReview review) {
        Document document = Jsoup.parse(review.getContent());
        for (Element img: document.select("img")) {
            img.remove();
        }
        review.setContent(document.text().replaceAll("\\s+", " ").trim());
        return new ExhibitionReviewDto.RequestReviewList(review);
    }


}
