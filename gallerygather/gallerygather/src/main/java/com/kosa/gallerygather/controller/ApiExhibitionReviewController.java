package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.ExhibitionReviewReplyService;
import com.kosa.gallerygather.service.ExhibitionReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exhibition")
public class ApiExhibitionReviewController {

    private final ExhibitionReviewService reviewService;
    private final ExhibitionReviewReplyService reviewReplyService;

    @PostMapping("/{exhibitionId}/review")
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

    @GetMapping("/{exhibitionId}/review")
    public ResponseEntity<List<ExhibitionReviewDto.RequestReviewList>> getExhibitionReview(@PathVariable Long exhibitionId,
                                                                                           @ModelAttribute PageRequestDto pageRequestDto) {

        List<ExhibitionReviewDto.RequestReviewList> reviewList = reviewService.getExhibitionReviews(exhibitionId, pageRequestDto);
        return ResponseEntity.ok(reviewList);
    }

    @PostMapping("/review/{reviewId}/replies")
    public ResponseEntity<Page<ExhibitionReviewReplyDto.ExhibitionReviewReplyResponseDto>> addCommentToReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                                              @PathVariable Long reviewId,
                                                                                                              @RequestBody ExhibitionReviewReplyDto.ExhibitionReviewRequestDto request) {
        if (userDetails == null) {
            throw new UsernameNotFoundException("회원 정보를 확인할 수 없습니다.");
        }

        return ResponseEntity.ok(reviewReplyService.addCommentToReview(userDetails.getEmail(), reviewId, request));
    }

}
