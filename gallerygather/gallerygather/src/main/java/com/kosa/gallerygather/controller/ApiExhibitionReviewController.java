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

    @GetMapping
    public ResponseEntity<List<ExhibitionReviewDto.RequestReviewList>> getExhibitionReview(@PathVariable Long exhibitionId,
                                                                                           @ModelAttribute PageRequestDto pageRequestDto) {

        List<ExhibitionReviewDto.RequestReviewList> reviewList = reviewService.getExhibitionReviews(exhibitionId, pageRequestDto);
        return ResponseEntity.ok(reviewList);
    }

//@Slf4j
//@RequestMapping("/api/exhibition/{exhibitionId}/review")
//public class ApiExhibitionReviewController {
//    private final ExhibitionReviewService exhibitionReviewService;
//    // @ModelAttribute -> Request Parameter -> Object, value
//    // @RequestBody -> Http Body -> Json -> Object, value
//    @PostMapping
//    public ResponseEntity<ReviewDetailDto> postExhibitionReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                                                                @PathVariable Long exhibitionId,
//                                                                @ModelAttribute ExhibitionReviewRequestDto requestDto) {
//        ReviewDetailDto reviewDetailDto = exhibitionReviewService.addReviewToExhibition(userDetails.getEmail(), exhibitionId, requestDto);
//        return ResponseEntity.ok(reviewDetailDto);
//    }

}
