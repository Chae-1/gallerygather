package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.entity.*;
import com.kosa.gallerygather.repository.*;
import jakarta.persistence.EntityManager;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final EntityManager em;

    @Transactional
    public ReviewDetailDto write(final ExhibitionReviewRequestDto requestDto, String memberEmail, Long exhibitionId) {

        System.out.println("============== review Details Dto 시작 =================");

        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new IllegalArgumentException("전시회 ID 찾기 오류: " + exhibitionId));

        ExhibitionReview savedReview = exhibitionReviewRepository.save(requestDto
                .toEntity(member, exhibition));

        exhibition.updateAvgRating(savedReview.getRating());
        System.out.println("=======================================================" + savedReview);

        // 이미지 저장하고 tbl_review 와 연결
        List<ReviewImage> images = requestDto
                .getImages()
                .stream()
                .map(ReviewImageResponseDto -> {
                    ReviewImage reviewImage = new ReviewImage();
                    reviewImage.setOriginalName(ReviewImageResponseDto.getOriginalName());
                    reviewImage.setPath(ReviewImageResponseDto.getPath());
                    savedReview.addImage(reviewImage);
                    return reviewImage;
                }).collect(Collectors.toList());

        // 리뷰에 이미지 리스트 설정
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

    // 리뷰 삭제
    @Transactional
    public boolean deleteReview(String memberEmail, Long reviewId) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰 ID 찾기 오류: " + reviewId));
        if (review.getMember().equals(member)) {
            exhibitionReviewRepository.delete(review);
            return true;
        }
        return false;
    }

    // 리뷰 수정
    @Transactional
    public ReviewDetailDto updateReview(ExhibitionReviewRequestDto reviewDto,String memberEmail, Long reviewId, Long exhibitionId) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID(Email) 찾기 오류: " + memberEmail));
        ExhibitionReview review = exhibitionReviewRepository.findWithAllImagesById(reviewId)
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

        // 리뷰 수정
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());
        review.setViewDate(reviewDto.getViewDate());

        // 삭제할 이미지 처리
        if (reviewDto.getImagesToDelete() != null && !reviewDto.getImagesToDelete().isEmpty()) {
            for (String imageUrl : reviewDto.getImagesToDelete()) {
                // /uploads/ 이후의 경로만 추출
                String relativePath = imageUrl.substring(imageUrl.indexOf("/uploads/"));

                // 해당 경로로 이미지 찾기
                ReviewImage reviewImage = reviewImageRepository.findByPath(relativePath)
                        .orElseThrow(() -> new IllegalArgumentException("이미지 경로 찾기 오류: " + relativePath));

                // 리뷰에서 이미지 제거 및 DB에서 삭제
                review.removeImage(reviewImage);
                reviewImageRepository.delete(reviewImage);
            }
        }

        // 새로 추가된 이미지 처리
        if (reviewDto.getImages() != null && !reviewDto.getImages().isEmpty()) {
            List<ReviewImage> newImages = reviewDto.getImages().stream()
                    .map(imageDto -> {
                        ReviewImage reviewImage = new ReviewImage();
                        reviewImage.setOriginalName(imageDto.getOriginalName());
                        reviewImage.setPath(imageDto.getPath());
                        reviewImage.setExhibitionReview(review);
                        return reviewImage;
                    })
                    .collect(Collectors.toList());

            // 이미지 엔티티 저장
            reviewImageRepository.saveAll(newImages);

            // 리뷰에 이미지 추가
            for (ReviewImage newImage : newImages) {
                review.addImage(newImage);
            }
        }

        // 변경된 리뷰와 이미지를 DB에 저장
        exhibitionReviewRepository.saveAndFlush(review);

        // 평균 평점 업데이트
        exhibition.updateAvgRating(review.getRating());
        exhibitionRepository.saveAndFlush(exhibition);

        return new ReviewDetailDto(review, member, exhibition, review.getImages());
/*
        if (!review.isWriteFor(member)) {
            throw new RuntimeException("리뷰 수정 실패.");
        }

        ExhibitionReview updatedReview = review.update(requestDto, member, exhibition);
        exhibition.updateAvgRating(updatedReview.getRating());

        int deleteCount = reviewImageRepository.deleteByExhibitionReview(review);

        ExhibitionReview findReview = exhibitionReviewRepository.findById(reviewId)
                .get();

        System.out.println("deleteCount = " + deleteCount);

        List<ReviewImage> updatedImages = requestDto
                .getImages()
                .stream()
                .map(images -> {
                    ReviewImage image = ReviewImage.ofNewImage(images.getPath(), images.getOriginalName());
                    findReview.addImage(image);
                    return image;
                })
                .toList();
*/
    }


    // 작성자: 오지수
    // 전시 상세 페이지에서 하단의 리뷰 리스트를 가져오는 Service
    public Page<ExhibitionReviewDto.RequestReviewList> getExhibitionReviews(Long exhibitionId, PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto
                .getPageNo() - 1, pageRequestDto.getPagePer(), Sort.by("regDate").descending());
        Page<ExhibitionReview> exhibitionReviews = exhibitionReviewRepository.findByExhibitionId(exhibitionId, pageable);
        return exhibitionReviews.map(this::changeDtoRemoveImgTags);
    }

    /*
    작성자: 오지수
    ExhibitionReview를 받아서 Content에서 img 태그를 없애고 ExhibitionReviewDto로 반환
     */
    private ExhibitionReviewDto.RequestReviewList changeDtoRemoveImgTags(ExhibitionReview review) {
        Document document = Jsoup.parse(review.getContent());
        for (Element img : document.select("img")) {
            img.remove();
        }
        review.setContent(document.text().replaceAll("\\s+", " ").trim());
        return new ExhibitionReviewDto.RequestReviewList(review);
    }

    /*
    작성자: 오지수
    Review 좋아요 클릭
     */
    @Transactional
    public void clickReviewLike(ExhibitionReviewLikeDto.RequestReviewLikeDto reviewLikeDto, boolean isLike) throws Exception {
        // 존재하는 리뷰인지 확인
        System.out.println("service로 잘 넘어왔다. ++ isLike? : " + isLike);
        ExhibitionReview review = exhibitionReviewRepository.findById(reviewLikeDto.getReviewId())
                .orElseThrow(IllegalArgumentException::new);
        System.out.println("존재하는 리뷰인지 확인 *************** :" + review.getId());
        //
        if (isLike) { // 있으면 delete
            ReviewLike reviewLike = exhibitionReviewLikeRepository.findByMemberIdAndExhibitionReview(reviewLikeDto.getMemberId(), review)
                    .orElseThrow(IllegalArgumentException::new);
            exhibitionReviewLikeRepository.delete(reviewLike);
            review.decreaseLikeCount();
        } else { // 없으면 insert
            boolean result = exhibitionReviewLikeRepository.existsByMemberIdAndExhibitionReviewId(reviewLikeDto.getMemberId(), reviewLikeDto.getReviewId());
            if (result) {
                throw new Exception("잘못된 접근입니다.");
            } else {
                ReviewLike reviewLike = ReviewLike.setReviewLike(reviewLikeDto.getMemberId(), reviewLikeDto.getReviewId());
                exhibitionReviewLikeRepository.save(reviewLike);
                review.increaseLikeCount();
            }
        }

    }


}
