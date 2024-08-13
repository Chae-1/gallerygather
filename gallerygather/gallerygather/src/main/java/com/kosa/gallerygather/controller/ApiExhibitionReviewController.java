package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.repository.ExhibitionReviewReplyRepository;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.ExhibitionReviewReplyService;
import com.kosa.gallerygather.service.ExhibitionReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exhibition")
public class ApiExhibitionReviewController {

    private final ExhibitionReviewService reviewService;
    private final ExhibitionReviewReplyService reviewReplyService;
    private final ExhibitionReviewReplyRepository exhibitionReviewReplyRepository;
    private final ExhibitionReviewReplyService exhibitionReviewReplyService;

    @PostMapping("/{exhibitionId}/review")
    public ResponseEntity<ReviewDetailDto> createReview(@RequestBody ExhibitionReviewRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long exhibitionId) {
        String memberId = userDetails.getEmail();
        ReviewDetailDto detailDto = reviewService.write(requestDto, memberId, exhibitionId);

        return ResponseEntity.ok(detailDto);

    }

    @GetMapping("/{exhibitionId}/review/{reviewId}")
    public ResponseEntity<ReviewDetailDto> getReviewDetail(@PathVariable Long exhibitionId,
                                                           @PathVariable Long reviewId) {
        ReviewDetailDto detailDto = reviewService.getReviewDetail(exhibitionId, reviewId);
        return ResponseEntity.ok(detailDto);
    }

    /*
    작성자: 오지수
    전시 상세 페이지에서 페이지네이션으로 리뷰 정보 가져오기
     */
    @GetMapping("/{exhibitionId}/review")
    public ResponseEntity<Page<ExhibitionReviewDto.RequestReviewList>> getExhibitionReview(@PathVariable Long exhibitionId,
                                                                                           @ModelAttribute PageRequestDto pageRequestDto) {
        System.out.println("댓글 리스트  호출");
        Page<ExhibitionReviewDto.RequestReviewList> reviewList = reviewService.getExhibitionReviews(exhibitionId, pageRequestDto);
        return ResponseEntity.ok(reviewList);
    }

    /*
    댓글 등록
     */
    @PostMapping("/review/{reviewId}/replies")
    public ResponseEntity<String> addCommentToReview(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                   @PathVariable Long reviewId,
                                                                   @RequestBody ExhibitionReviewReplyDto.ExhibitionReviewRequestDto request) {
        if (userDetails == null) {
            throw new UsernameNotFoundException("회원 정보를 확인할 수 없습니다.");
        }
        try {
            reviewReplyService.addCommentToReview(userDetails.getEmail(), reviewId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 정상적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 등록에 실패하였습니다.");
        }
    }

    // / -> /mypath
    // /api/exhibition/{exhibitionId}/review/{reviewId}/replies
    @GetMapping("/{exhibitionId}/review/{reviewId}/replies")
    public ResponseEntity<Page<ExhibitionReviewReplyDto
            .ExhibitionReviewReplyResponseDto>> findAllRepliesOnReview(
            @PathVariable Long reviewId,
            @PathVariable Long exhibitionId,
            Pageable pageable
            ) {
        System.out.println("페이지 ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ " + pageable);
        return ResponseEntity.ok(reviewReplyService.findAllRepliesAboutReview(reviewId, pageable));
    }

    /*
    작성자: 오지수
    댓글 수정 요청
     */
    @PutMapping("/reviews/{reviewId}/replies/{replyId}")
    public ResponseEntity<String> updateReplyWithId(@PathVariable Long reviewId,
                                                    @PathVariable Long replyId,
                                                    @RequestBody ExhibitionReviewReplyDto.updateReplyDto replyDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("******매핑이 잘 왔는지 확인*******");
        try {
            replyDto.setReplyId(replyId);
            exhibitionReviewReplyService.updateReplyWithId(reviewId, userDetails.getId(), replyDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 정상적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정에 실패하였습니다.");
        }
    }

    /*
    작성자: 오지수
    댓글 삭제 요청
     */
    @DeleteMapping("/reviews/{reviewId}/replies/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long reviewId,
                                              @PathVariable Long replyId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            exhibitionReviewReplyService.deleteRplyWithId(reviewId, userDetails.getId(), replyId);
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 정상적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패");
        }
    }

}
