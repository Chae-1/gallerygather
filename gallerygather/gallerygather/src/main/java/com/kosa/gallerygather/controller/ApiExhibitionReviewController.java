package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.ExhibitionReviewDto;
import com.kosa.gallerygather.dto.ExhibitionReviewRequestDto;
import com.kosa.gallerygather.dto.ReviewDetailDto;
import com.kosa.gallerygather.dto.PageRequestDto;
import com.kosa.gallerygather.entity.ReviewImage;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.ExhibitionReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exhibition/{exhibitionId}/review")
public class ApiExhibitionReviewController {

    private final ExhibitionReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDetailDto> createReview(@RequestBody ExhibitionReviewRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long exhibitionId) {
        String memberId = userDetails.getEmail();
        ReviewDetailDto detailDto = reviewService.write(requestDto, memberId, exhibitionId);

        return ResponseEntity.ok(detailDto);

    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailDto> getReviewDetail(@PathVariable Long exhibitionId, @PathVariable Long reviewId) {
        ReviewDetailDto detailDto = reviewService.getReviewDetail(exhibitionId,reviewId);
        return ResponseEntity.ok(detailDto);
    }

    /*
    작성자: 오지수
    전시 상세 페이지에서 페이지네이션으로 리뷰 정보 가져오기
     */
    @GetMapping
    public ResponseEntity<List<ExhibitionReviewDto.RequestReviewList>> getExhibitionReview(@PathVariable Long exhibitionId,
                                                                                           @ModelAttribute PageRequestDto pageRequestDto) {

        List<ExhibitionReviewDto.RequestReviewList> reviewList = reviewService.getExhibitionReviews(exhibitionId, pageRequestDto);
        return ResponseEntity.ok(reviewList);
    }

}
