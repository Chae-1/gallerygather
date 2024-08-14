package com.kosa.gallerygather.service;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.entity.*;
import com.kosa.gallerygather.repository.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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
