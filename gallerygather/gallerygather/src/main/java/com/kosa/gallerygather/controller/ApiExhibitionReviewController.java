package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.dto.*;
import com.kosa.gallerygather.security.UserDetailsImpl;
import com.kosa.gallerygather.service.ExhibitionReviewReplyService;
import com.kosa.gallerygather.service.ExhibitionReviewService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exhibition")
public class ApiExhibitionReviewController {

    private final ExhibitionReviewService reviewService;
    private final ExhibitionReviewReplyService reviewReplyService;
    private final ExhibitionReviewService exhibitionReviewService;
    private final ExhibitionReviewReplyService exhibitionReviewReplyService;

    @PostMapping("/{exhibitionId}/review")
    public ResponseEntity<ReviewDetailDto> createReview(@RequestBody ExhibitionReviewRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long exhibitionId) {
        String memberId = userDetails.getEmail();
        ReviewDetailDto detailDto = reviewService.write(requestDto, memberId, exhibitionId);
        return ResponseEntity.ok(detailDto);
    }

    /*
    작성자 : 오지수, 이혜은
    리뷰 정보 가져오기
     */
    @GetMapping("/{exhibitionId}/review/{reviewId}")
    public ResponseEntity<Map<String, Object>> getReviewDetail(@PathVariable Long exhibitionId,
                                                               @PathVariable Long reviewId,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok( reviewService.getReviewDetail(exhibitionId, reviewId, userDetails==null? null : userDetails.getId() ) );
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
        System.out.println(requestDto);
        ReviewDetailDto updatedReview = exhibitionReviewService.updateReview(requestDto, memberId, reviewId, exhibitionId);
        return ResponseEntity.ok(updatedReview);
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

    /*
    작성자: 오지수
    리뷰 좋아요 클릭
     */
    @PostMapping("/reviews/{reviewId}/like")
    public ResponseEntity<String> clickReviewLike(@PathVariable Long reviewId,
                                                  @RequestBody ExhibitionReviewLikeDto.RequestLike requestLike,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            exhibitionReviewService.clickReviewLike(new ExhibitionReviewLikeDto.RequestReviewLikeDto(userDetails.getId(), reviewId), requestLike.getIsLike());
            return ResponseEntity.status(HttpStatus.CREATED).body("좋아요 클릭을 성공적으로 반영하였습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좋아요 실패");
        }
    }

}
