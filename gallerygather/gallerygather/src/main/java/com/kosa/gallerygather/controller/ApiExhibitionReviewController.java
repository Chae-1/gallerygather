package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.entity.ExhibitionReview;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.ExhibitionReviewReplyService;
import com.kosa.gallerygather.service.ExhibitionReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    private final ExhibitionReviewService exhibitionReviewService;

    @PostMapping("/{exhibitionId}/review")
    public ResponseEntity<ReviewDetailDto> createReview(@RequestBody ExhibitionReviewRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long exhibitionId) {
        String memberId = userDetails.getEmail();
        ReviewDetailDto detailDto = reviewService.write(requestDto, memberId, exhibitionId);

        return ResponseEntity.ok(detailDto);

    }

    @GetMapping("/{exhibitionId}/review/{reviewId}")
    public ResponseEntity<ReviewDetailDto> getReviewDetail(@PathVariable Long exhibitionId, @PathVariable Long reviewId) {
        ReviewDetailDto detailDto = reviewService.getReviewDetail(exhibitionId,reviewId);
        return ResponseEntity.ok(detailDto);
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    public ResponseEntity<String> deleteReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PathVariable Long reviewId){
        String memberId = userDetails.getEmail();
        boolean isDeleted = reviewService.deleteReview(memberId,reviewId);

        if(isDeleted){
            return ResponseEntity.ok("리뷰 삭제 성공");
        } return ResponseEntity.status(HttpStatus.FORBIDDEN).body("리뷰 삭제 오류");
    }

    @PutMapping("/{exhibitionId}/updateReview/{reviewId}")
    public ResponseEntity<ReviewDetailDto> updateReview(@RequestBody ExhibitionReviewRequestDto requestDto,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @PathVariable Long reviewId,
                                               @PathVariable Long exhibitionId) {
        String memberId = userDetails.getEmail();
        ReviewDetailDto updatedReview = exhibitionReviewService.updateReview(requestDto, memberId, reviewId, exhibitionId);
        return ResponseEntity.ok(updatedReview);
    }

    /*
    작성자: 오지수
    전시 상세 페이지에서 페이지네이션으로 리뷰 정보 가져오기
     */
    @GetMapping("/{exhibitionId}/review")
    public ResponseEntity<List<ExhibitionReviewDto.RequestReviewList>> getExhibitionReview(@PathVariable Long exhibitionId,
                                                                                           @ModelAttribute PageRequestDto pageRequestDto) {
        System.out.println("댓글 리스트  호출");
        List<ExhibitionReviewDto.RequestReviewList> reviewList = reviewService.getExhibitionReviews(exhibitionId, pageRequestDto);
        return ResponseEntity.ok(reviewList);
    }

    @PostMapping("/review/{reviewId}/replies")
    public ResponseEntity<Page<ExhibitionReviewReplyDto.ExhibitionReviewReplyResponseDto>> addCommentToReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                                              @PathVariable Long reviewId  ,
                                                                                                              @RequestBody ExhibitionReviewReplyDto.ExhibitionReviewRequestDto request) {
        if (userDetails == null) {
            throw new UsernameNotFoundException("회원 정보를 확인할 수 없습니다.");
        }

        return ResponseEntity.ok(reviewReplyService.addCommentToReview(userDetails.getEmail(), reviewId, request));
    }

}
